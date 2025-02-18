package com.utmaximur.calendar.models

import kotlinx.datetime.DayOfWeek
import platform.Foundation.NSCalendar

private val iosOrder = listOf(
    DayOfWeek.SUNDAY,
    DayOfWeek.MONDAY,
    DayOfWeek.TUESDAY,
    DayOfWeek.WEDNESDAY,
    DayOfWeek.THURSDAY,
    DayOfWeek.FRIDAY,
    DayOfWeek.SATURDAY
)

actual fun DayOfWeek.localized(): String =
    NSCalendar.currentCalendar.veryShortWeekdaySymbols[iosOrder.indexOf(this)].toString()

actual fun firstDayOfWeek(): DayOfWeek = when (NSCalendar.currentCalendar.firstWeekday) {
    1uL -> DayOfWeek.SUNDAY
    2uL -> DayOfWeek.MONDAY
    3uL -> DayOfWeek.TUESDAY
    4uL -> DayOfWeek.WEDNESDAY
    5uL -> DayOfWeek.THURSDAY
    6uL -> DayOfWeek.FRIDAY
    7uL -> DayOfWeek.SATURDAY
    else -> DayOfWeek.SUNDAY
}
