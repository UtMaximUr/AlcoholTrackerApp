package com.utmaximur.calendar.ui.week

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlinx.datetime.LocalDate

data class DateRangeConfig(
    val currentDateActiveBackgroundColor: Color,
    val currentDateInactiveBackgroundColor: Color,
    val selectedDateBackgroundColor: Color,
    val selectedDateTextColor: Color,
    val unselectedDateTextColor: Color,
    val heightContainer: Dp,
    val horizontalPadding: Dp,
    val topPadding: Dp,
    val bottomPadding: Dp
) {
    @Composable
    fun dateTextColor(
        currentDate: LocalDate,
        selectedDate: LocalDate,
        date: LocalDate
    ): Color = with(this) {
        return@with when {
            selectedDate == date && currentDate == date -> selectedDateTextColor
            selectedDate == date -> selectedDateTextColor
            else -> unselectedDateTextColor
        }
    }

    @Composable
    fun dateBackgroundColor(
        currentDate: LocalDate,
        selectedDate: LocalDate,
        date: LocalDate
    ): Color = with(this) {
        return@with when {
            selectedDate == date && currentDate == date -> currentDateActiveBackgroundColor
            selectedDate == date -> currentDateActiveBackgroundColor
            currentDate == date -> currentDateInactiveBackgroundColor
            else -> Color.Transparent
        }
    }
}