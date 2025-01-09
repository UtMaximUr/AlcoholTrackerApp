package com.utmaximur.calendar.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.calendar.models.CalendarView
import com.utmaximur.calendar.store.CalendarStore.Intent
import com.utmaximur.calendar.store.CalendarStore.Label
import com.utmaximur.calendar.store.CalendarStore.State
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.domain.EMPTY_STRING

interface CalendarStore : Store<Intent, State, Label> {

    data class State(
        val calendarView: CalendarView,
        val requestTracksUi: RequestUi<TracksData>,
        val currency: String
    ) {
        constructor() : this(
            calendarView = CalendarView.MONTH_VIEW,
            requestTracksUi = RequestUi(),
            currency = EMPTY_STRING
        )
    }

    sealed interface Intent {

        data object ToggleCalendarView : Intent
    }

    sealed interface Label
}