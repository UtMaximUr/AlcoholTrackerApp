package com.utmaximur.calendar.models

enum class CalendarView {
    DAY_VIEW,
    MONTH_VIEW;

    fun toggle() = when (this) {
        DAY_VIEW -> MONTH_VIEW
        MONTH_VIEW -> DAY_VIEW
    }
}