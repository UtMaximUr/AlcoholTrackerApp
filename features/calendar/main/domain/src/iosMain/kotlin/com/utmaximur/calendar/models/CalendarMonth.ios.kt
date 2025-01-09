package com.utmaximur.calendar.models

import kotlinx.datetime.Month
import platform.Foundation.NSCalendar


actual fun CalendarMonth.localized(): String {
    return "${month.localized()} $year"
}

actual fun Month.localized(): String {
    return NSCalendar.currentCalendar
        .standaloneMonthSymbols[ordinal]
        .toString()
}

