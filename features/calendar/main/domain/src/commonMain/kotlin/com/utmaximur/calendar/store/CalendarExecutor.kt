package com.utmaximur.calendar.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.calendar.analytic_events.OpenScreenEvent
import com.utmaximur.calendar.models.CalendarView
import com.utmaximur.calendar.store.CalendarStore.Intent
import com.utmaximur.calendar.store.CalendarStore.Label
import com.utmaximur.calendar.store.CalendarStore.State
import com.utmaximur.core.mvi_mapper.Request
import com.utmaximur.core.mvi_mapper.asRequest
import com.utmaximur.domain.calendar.CalendarRepository
import com.utmaximur.domain.models.Track
import com.utmaximur.utils.extensions.toLocalDate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

typealias TracksData = Map<LocalDate, List<Track>>

internal sealed interface Message {
    data class UpdateState(val request: Request<TracksData>) : Message
    data class UpdateCalendarView(val calendarView: CalendarView) : Message
    data class UpdateCurrency(val currency: String) : Message
}

internal class CalendarExecutor(
    private val repository: CalendarRepository,
    private val analyticsManager: AnalyticsManager
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {

    override fun executeAction(action: Unit) {
        scope.launch { analyticsManager.trackEvent(OpenScreenEvent()) }
        repository.currencyStream
            .onEach { currency -> dispatch(Message.UpdateCurrency(currency)) }
            .launchIn(scope)
        repository.observeTracksByStartDate()
            .map { tracks -> tracks.groupBy { it.date } }
            .map { tracks -> tracks.mapKeys { (date, _) -> date.toLocalDate() } }
            .asRequest()
            .onEach { tracks -> dispatch(Message.UpdateState(tracks)) }
            .launchIn(scope)
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            Intent.ToggleCalendarView -> toggleCalendarView()
        }
    }

    private fun toggleCalendarView() {
        val calendarView = when (state().calendarView) {
            CalendarView.DAY_VIEW -> CalendarView.MONTH_VIEW
            CalendarView.MONTH_VIEW -> CalendarView.DAY_VIEW
        }
        dispatch(Message.UpdateCalendarView(calendarView))
    }
}