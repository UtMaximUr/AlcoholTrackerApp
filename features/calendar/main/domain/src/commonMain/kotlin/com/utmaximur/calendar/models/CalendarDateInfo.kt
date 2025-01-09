package com.utmaximur.calendar.models

import kotlinx.datetime.LocalDate

class CalendarDateInfo(
    val dateMatrix: List<List<LocalDate>>,
    val currentMonth: CalendarMonth
)