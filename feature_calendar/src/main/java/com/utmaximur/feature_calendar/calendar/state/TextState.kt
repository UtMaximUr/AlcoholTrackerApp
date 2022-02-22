package com.utmaximur.feature_calendar.calendar.state

import com.utmaximur.feature_calendar.R


sealed class TextState {

    data class SelectDay(val textId: Int = R.string.calendar_select_date) : TextState()
    data class TracksEmpty(val textId: Int = R.string.calendar_empty) : TextState()
    data class AddPressToStart(val textId: Int = R.string.calendar_add_press) : TextState()

}