package com.utmaximur.calendar.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.number
import kotlin.math.abs
import kotlin.math.floor

data class CalendarMonth(
    val year: Int,
    val month: Month
) : Comparable<CalendarMonth> {

    val numberOfDays = length(isLeapYear(year.toLong()))

    private fun length(leapYear: Boolean): Int {
        return when (month) {
            Month.FEBRUARY -> if (leapYear) 29 else 28
            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
            else -> 31
        }
    }

    override fun compareTo(other: CalendarMonth): Int {
        var cmp = year - other.year
        if (cmp == 0) {
            cmp = month.number - other.month.number
        }
        return cmp
    }
}

expect fun CalendarMonth.localized(): String

expect fun Month.localized(): String

fun CalendarMonth.addMonths(monthsToAdd: Int): CalendarMonth {
    if (monthsToAdd == 0) return this
    val monthCount = year * 12 + (month.number - 1)
    val calcMonths = monthCount + monthsToAdd
    val newYear = floor(calcMonths.div(12.0)).toInt()
    val newMonth = floor(calcMonths.mod(12).toDouble()).toInt() + 1
    return CalendarMonth(newYear, Month(newMonth))
}

fun CalendarMonth.startRangeMonth() = CalendarMonth(year.dec(), Month.JANUARY)

fun CalendarMonth.endRangeMonth() = CalendarMonth(year.inc(), Month.DECEMBER)

fun CalendarMonth.previous(): CalendarMonth = addMonths(-1)

fun CalendarMonth.next(): CalendarMonth = addMonths(1)

val LocalDate.CalendarMonth: CalendarMonth get() = CalendarMonth(year, month)

fun CalendarMonth.atDay(dayOfMonth: Int) = LocalDate(year, month, dayOfMonth)

fun CalendarMonth.lastDayOfWeek() = atDay(numberOfDays).dayOfWeek

fun isLeapYear(year: Long): Boolean {
    return year and 3L == 0L && (year % 100 != 0L || year % 400 == 0L)
}

operator fun CalendarMonth.contains(date: LocalDate): Boolean {
    val start = LocalDateTime(year, month, 1, 0, 0, 0).date
    val end = LocalDateTime(year, month, numberOfDays, 23, 59, 59).date
    return date in start..end
}

fun ClosedRange<CalendarMonth>.getByIndex(index: Int) = start.addMonths(
    if (start < endInclusive) index
    else -index
)

fun ClosedRange<CalendarMonth>.indexOf(month: CalendarMonth): Int? {
    val (start, end) = if (start < endInclusive) start to month else endInclusive to month
    val startMonthCount = start.year * 12 + start.month.number
    val endMonthCount = end.year * 12 + end.month.number
    val result = endMonthCount - startMonthCount
    return if (result < 0 || result >= size()) null else result
}

fun ClosedRange<CalendarMonth>.size(): Int {
    val startMonthCount = start.year * 12 + start.month.number
    val endMonthCount = endInclusive.year * 12 + endInclusive.month.number
    return abs(endMonthCount - startMonthCount) + 1
}
