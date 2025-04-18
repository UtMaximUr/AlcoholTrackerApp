package com.utmaximur.calendar

import com.utmaximur.calendar.store.CalendarStore
import com.utmaximur.core.decompose.ComposeComponent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

interface CalendarComponent : ComposeComponent {

    val model: StateFlow<CalendarStore.State>

    fun handleVisibleDateRange(firstDate: LocalDate, lastDate: LocalDate)

    fun onCreateTrackClick()

    fun toggleView()

    fun onTrackClick(trackId: Long)

    sealed interface Output {

        data object NavigateCreateTrack : Output

        data class NavigateDetailTrack(val trackId: Long) : Output
    }
}
