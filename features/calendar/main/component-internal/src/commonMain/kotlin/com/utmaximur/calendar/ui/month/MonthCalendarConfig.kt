package com.utmaximur.calendar.ui.month

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.datetime.LocalDate

data class MonthCalendarConfig(
    val currentDateColor: Color,
    val dateColor: Color
) {
    @Composable
    fun dateTextColor(
        currentDate: LocalDate,
        date: LocalDate
    ): Color = with(this) {
        return@with when {
            date == currentDate -> currentDateColor
            else -> dateColor
        }
    }
}