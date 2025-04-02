package com.utmaximur.createTrack.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.core.mvi_mapper.Request
import com.utmaximur.core.mvi_mapper.asRequest
import com.utmaximur.createTrack.analytic_events.OpenScreenEvent
import com.utmaximur.createTrack.interactor.CreateTrack
import com.utmaximur.createTrack.store.CreateTrackStore.Intent
import com.utmaximur.createTrack.store.CreateTrackStore.Label
import com.utmaximur.createTrack.store.CreateTrackStore.State
import com.utmaximur.domain.calculator.CalculatorProviderData
import com.utmaximur.domain.confirmDialog.ConfirmDialogProviderData
import com.utmaximur.domain.createTrack.CreateTrackRepository
import com.utmaximur.domain.datePicker.DateProviderData
import com.utmaximur.domain.models.Drink
import com.utmaximur.domain.models.TrackData
import com.utmaximur.message.models.MessageContainer
import com.utmaximur.message.models.MessageService
import com.utmaximur.utils.extensions.getTodayDateUi
import com.utmaximur.utils.extensions.parseToLong
import com.utmaximur.utils.extensions.toDateUi
import createTrack.domain.resources.Res
import createTrack.domain.resources.saving_error
import createTrack.domain.resources.successful_save
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString


internal sealed interface Message {
    data class UpdateState(val request: Request<List<Drink>>) : Message
    data class UpdatePrice(val price: Float) : Message
    data class UpdateTotalPrice(val totalPrice: String) : Message
    data class UpdateSelectedDate(val date: String) : Message
    data class UpdateCurrency(val currency: String) : Message
}

internal class CreateTrackExecutor(
    private val repository: CreateTrackRepository,
    private val interactor: CreateTrack,
    private val calculatorProviderData: CalculatorProviderData,
    private val dateProviderData: DateProviderData,
    private val confirmDialogProviderData: ConfirmDialogProviderData,
    private val messageService: MessageService,
    private val analyticsManager: AnalyticsManager
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {

    private var savingJob: Job? = null

    override fun executeAction(action: Unit) {
        trackScreenOpen()
        observePriceUpdates()
        observeDateUpdates()
        observeDrinks()
        observeCurrency()
        observeConfirmDialog()
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.SaveTrackData -> handleSaveTrackData(intent.trackData)
            is Intent.SelectedDate -> handleDatePicker(intent.date)
            Intent.Today -> handleTodayIntent()
        }
    }

    private fun trackScreenOpen() = scope.launch {
        analyticsManager.trackEvent(OpenScreenEvent())
    }

    private fun observePriceUpdates() = calculatorProviderData.dataFlow
        .filterNotNull()
        .onEach { price -> dispatch(Message.UpdatePrice(price)) }
        .launchIn(scope)

    private fun observeDateUpdates() = dateProviderData.dataFlow
        .filterNotNull()
        .map { date -> date.toDateUi() }
        .onEach { dateUi -> handleSelectedDate(dateUi) }
        .launchIn(scope)

    private fun observeDrinks() = repository.drinksStream
        .asRequest()
        .onEach { drinks -> dispatch(Message.UpdateState(drinks)) }
        .launchIn(scope)

    private fun observeCurrency() = repository.currencyStream
        .onEach { currency -> dispatch(Message.UpdateCurrency(currency)) }
        .launchIn(scope)

    private fun observeConfirmDialog() = confirmDialogProviderData.dataFlow
        .filterNotNull()
        .onEach { id -> repository.deleteDrink(id) }
        .launchIn(scope)

    private fun handleSaveTrackData(trackData: TrackData) {
        savingJob?.cancel()
        savingJob = scope.launch { processTrackData(trackData) }
    }

    private suspend fun processTrackData(trackData: TrackData) {
        interactor.invoke(trackData)
            .onFailure { error -> handleSaveError(error) }
            .onSuccess { handleSaveSuccess() }
    }

    private suspend fun handleSaveError(error: Throwable) {
        showMessage(Res.string.saving_error, error.message)
    }

    private suspend fun handleSaveSuccess() {
        showMessage(Res.string.successful_save)
        publish(Label.CloseEvent)
    }

    private fun handleDatePicker(date: String) {
        publish(Label.DatePickerEvent(date.parseToLong()))
    }

    private fun handleTodayIntent() {
        handleSelectedDate(getTodayDateUi())
    }

    private fun handleSelectedDate(dateUi: String) {
        dispatch(Message.UpdateSelectedDate(dateUi))
        publish(Label.DateEvent(dateUi))
    }

    private suspend fun showMessage(res: StringResource, args: String? = null) {
        val message = getString(res, args.orEmpty())
        messageService.showMessage(MessageContainer.SimpleMessage(message))
    }
}