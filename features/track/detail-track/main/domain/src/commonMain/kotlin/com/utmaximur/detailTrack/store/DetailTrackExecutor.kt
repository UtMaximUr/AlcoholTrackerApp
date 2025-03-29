package com.utmaximur.detailTrack.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.detailTrack.analytic_events.OpenScreenEvent
import com.utmaximur.detailTrack.interactor.UpdateTrack
import com.utmaximur.detailTrack.store.DetailTrackStore.Intent
import com.utmaximur.detailTrack.store.DetailTrackStore.Label
import com.utmaximur.detailTrack.store.DetailTrackStore.State
import com.utmaximur.domain.calculator.CalculatorProviderData
import com.utmaximur.domain.confirmDialog.ConfirmDialogProviderData
import com.utmaximur.domain.datePicker.DateProviderData
import com.utmaximur.domain.detailTrack.DetailTrackRepository
import com.utmaximur.domain.models.Track
import com.utmaximur.domain.models.TrackData
import com.utmaximur.message.models.MessageContainer
import com.utmaximur.message.models.MessageService
import com.utmaximur.utils.extensions.getTodayDateUi
import com.utmaximur.utils.extensions.parseToLong
import com.utmaximur.utils.extensions.toDateUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


internal sealed interface Message {
    data class UpdateTrack(val track: Track) : Message
    data class UpdatePrice(val price: Float) : Message
    data class UpdateSelectedDate(val date: String) : Message
    data class UpdateCurrency(val currency: String) : Message
}

internal class DetailTrackExecutor(
    private val trackId: Long,
    private val repository: DetailTrackRepository,
    private val interactor: UpdateTrack,
    private val calculatorProviderData: CalculatorProviderData,
    private val dateProviderData: DateProviderData,
    private val confirmDialogProviderData: ConfirmDialogProviderData,
    private val analyticsManager: AnalyticsManager,
    private val messageService: MessageService
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {

    private var updateJob: Job? = null
        get() = field?.takeIf { it.isActive }

    override fun executeAction(action: Unit) {
        trackScreenOpen()
        observeTrackDetails()
        observePriceUpdates()
        observeDateUpdates()
        observeDeleteConfirmation()
        observeCurrency()
    }

    override fun executeIntent(intent: Intent) = when (intent) {
        is Intent.SaveTrackData -> saveTrack(intent.trackData)
        is Intent.SelectedDate -> handleDateSelection(intent.date)
        Intent.Today -> handleTodaySelection()
    }

    private fun trackScreenOpen() = scope.launch {
        analyticsManager.trackEvent(OpenScreenEvent())
    }

    private fun observeTrackDetails() = repository.observeTrackById(trackId)
        .onEach { track ->
            handleSelectedDate(track.date.toDateUi())
            dispatch(Message.UpdatePrice(track.price))
            dispatch(Message.UpdateTrack(track))
        }
        .launchIn(scope)

    private fun observePriceUpdates() = calculatorProviderData.dataFlow
        .filterNotNull()
        .onEach { price -> dispatch(Message.UpdatePrice(price)) }
        .launchIn(scope)

    private fun observeDateUpdates() = dateProviderData.dataFlow
        .filterNotNull()
        .map { date -> date.toDateUi() }
        .onEach { dateUi -> handleSelectedDate(dateUi) }
        .launchIn(scope)

    private fun observeDeleteConfirmation() = confirmDialogProviderData.dataFlow
        .filterNotNull()
        .onEach { id ->
            repository.deleteTrack(id)
            publish(Label.CloseEvent)
        }
        .launchIn(scope)

    private fun observeCurrency() = repository.currencyStream
        .onEach { currency -> dispatch(Message.UpdateCurrency(currency)) }
        .launchIn(scope)

    private fun handleSelectedDate(dateUi: String) {
        dispatch(Message.UpdateSelectedDate(dateUi))
        publish(Label.DateEvent(dateUi))
    }

    private fun saveTrack(trackData: TrackData) {
        updateJob?.cancel()
        updateJob = scope.launch {
            updateTrack(trackData)
        }
    }

    private suspend fun updateTrack(trackData: TrackData) {
        val params = UpdateTrack.Params(state().track, trackData)
        interactor.invoke(params)
            .onSuccess { publish(Label.CloseEvent) }
            .onFailure { error -> showErrorMessage(error) }
    }

    private fun handleDateSelection(date: String) {
        publish(Label.DatePickerEvent(date.parseToLong()))
    }

    private fun handleTodaySelection() {
        dispatch(Message.UpdateSelectedDate(getTodayDateUi()))
    }

    private fun showErrorMessage(error: Throwable) {
        val message = error.message.orEmpty()
        messageService.showMessage(MessageContainer.SimpleMessage(message))
    }
}