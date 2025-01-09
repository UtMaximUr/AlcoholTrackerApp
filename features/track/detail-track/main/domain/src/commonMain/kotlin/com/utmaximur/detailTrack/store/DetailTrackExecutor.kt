package com.utmaximur.detailTrack.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.core.mvi_mapper.Request
import com.utmaximur.core.mvi_mapper.asRequest
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
import com.utmaximur.utils.extensions.getTodayDateUi
import com.utmaximur.utils.extensions.parseToLong
import com.utmaximur.utils.extensions.toDateUi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


internal sealed interface Message {
    data class UpdateState(val request: Request<Track>) : Message
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
    private val analyticsManager: AnalyticsManager
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {

    override fun executeAction(action: Unit) {
        scope.launch { analyticsManager.trackEvent(OpenScreenEvent()) }
        calculatorProviderData.dataFlow
            .filterNotNull()
            .onEach { price -> dispatch(Message.UpdatePrice(price)) }
            .launchIn(scope)
        dateProviderData.dataFlow
            .filterNotNull()
            .onEach { date -> dispatch(Message.UpdateSelectedDate(date.toDateUi())) }
            .launchIn(scope)
        confirmDialogProviderData.dataFlow
            .filterNotNull()
            .onEach { id ->
                repository.deleteTrack(id)
                publish(Label.CloseEvent)
            }
            .launchIn(scope)
        repository.observeTrackById(trackId)
            .onEach { track ->
                dispatch(Message.UpdateSelectedDate(track.date.toDateUi()))
                dispatch(Message.UpdatePrice(track.price))
            }
            .asRequest()
            .onEach { dispatch(Message.UpdateState(it)) }
            .launchIn(scope)
        repository.currencyStream
            .onEach { currency -> dispatch(Message.UpdateCurrency(currency)) }
            .launchIn(scope)
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.SaveTrackData -> scope.launch {
                interactor.invoke(UpdateTrack.Params(trackId, intent.trackData))
                publish(Label.CloseEvent)
            }

            is Intent.SelectedDate -> publish(Label.DatePickerEvent(intent.date.parseToLong()))
            Intent.Today -> dispatch(Message.UpdateSelectedDate(getTodayDateUi()))
        }
    }
}