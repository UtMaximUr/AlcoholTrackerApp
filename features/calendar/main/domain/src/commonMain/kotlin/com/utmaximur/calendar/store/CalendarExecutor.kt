package com.utmaximur.calendar.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.calendar.analytic_events.OpenScreenEvent
import com.utmaximur.calendar.interactor.GetTracksByDateRange
import com.utmaximur.calendar.models.CalendarView
import com.utmaximur.calendar.store.CalendarStore.Intent
import com.utmaximur.calendar.store.CalendarStore.Label
import com.utmaximur.calendar.store.CalendarStore.State
import com.utmaximur.core.mvi_mapper.Request
import com.utmaximur.core.mvi_mapper.asRequest
import com.utmaximur.domain.Track
import com.utmaximur.domain.calendar.CalendarRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

typealias TracksData = Map<LocalDate, List<Track>>
typealias DateRange = Pair<LocalDate, LocalDate>

internal sealed interface Message {
    data class UpdateState(val request: Request<TracksData>) : Message
    data class UpdateCalendarView(val calendarView: CalendarView) : Message
    data class UpdateCurrency(val currency: String) : Message
}

internal class CalendarExecutor(
    private val repository: CalendarRepository,
    private val interactor: GetTracksByDateRange,
    private val analyticsManager: AnalyticsManager
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {

    private val currentDateRange = MutableStateFlow<DateRange?>(null)

    override fun executeAction(action: Unit) {
        trackScreenOpen()
        observeCurrency()
        observeTracks()
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            Intent.ToggleCalendarView -> toggleCalendarView()
            is Intent.DateRange -> currentDateRange.update {
                intent.firstDate to intent.lastDate
            }
        }
    }

    private fun trackScreenOpen() = scope.launch {
        analyticsManager.trackEvent(OpenScreenEvent())
    }

    private fun observeCurrency() = repository
        .currencyStream
        .onEach { currency -> dispatch(Message.UpdateCurrency(currency)) }
        .launchIn(scope)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeTracks() = currentDateRange
        .filterNotNull()
        .flatMapLatest(interactor::doWork)
        .asRequest()
        .onEach { tracks -> dispatch(Message.UpdateState(tracks)) }
        .launchIn(scope)

    private fun toggleCalendarView() {
        val calendarView = state().calendarView.toggle()
        dispatch(Message.UpdateCalendarView(calendarView))
    }
}