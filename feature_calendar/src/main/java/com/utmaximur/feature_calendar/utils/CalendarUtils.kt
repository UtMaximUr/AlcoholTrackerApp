package com.utmaximur.feature_calendar.utils

import android.content.Context
import com.utmaximur.domain.entity.CalendarDay
import com.utmaximur.domain.entity.EventDay
import com.utmaximur.feature_calendar.R
import com.utmaximur.utils.formatDate
import java.util.*

private val calendar = Calendar.getInstance()


internal fun Int.getMonthAndYearDate(context: Context, month: Int) = String.format(
    "%s  %s",
    context.resources.getStringArray(R.array.material_calendar_months_array)[month],
    this
)

fun backCalendarClick(currentMonth: Int, currentYear: Int, onResult: (Int, Int) -> Unit) {
    var month = currentMonth
    var year = currentYear
    if (currentMonth != 0) {
        month -= 1
    } else {
        month = 11
        year -= 1
    }
    onResult(month, year)
}

fun nextCalendarClick(currentMonth: Int, currentYear: Int, onResult: (Int, Int) -> Unit) {
    var month = currentMonth
    var year = currentYear
    if (currentMonth != 11) {
        month += 1
    } else {
        month = 0
        year += 1
    }
    onResult(month, year)
}

fun getCountDayPreviousMonth(currentYear: Int, currentMonth: Int): Int {
    calendar.set(currentYear, currentMonth, 1)
    return calendar[Calendar.DAY_OF_WEEK] - calendar.firstDayOfWeek
}

fun getDayPreviousMonth(currentYear: Int, currentMonth: Int, index: Int): Int {
    calendar.set(currentYear, currentMonth, 1)
    val dayOfWeek = calendar[Calendar.DAY_OF_WEEK] - calendar.firstDayOfWeek
    calendar.set(currentYear, currentMonth - 1, 1)
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - dayOfWeek + index + 1
}

fun getCountDayCurrentMonth(currentYear: Int, currentMonth: Int): Int {
    calendar.set(currentYear, currentMonth, 1)
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}

fun getCountDayNextMonth(currentYear: Int, currentMonth: Int): Int {
    calendar.set(currentYear, currentMonth, 1)
    val dayOfWeek = calendar[Calendar.DAY_OF_WEEK] - calendar.firstDayOfWeek
    val countDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val countDayNextMonth = 7 - (dayOfWeek + countDay) % 7
    return if (countDayNextMonth != 7) countDayNextMonth else 0
}

fun getCurrentDay(currentYear: Int, currentMonth: Int, day: CalendarDay): Int {
    return if (day.month == currentMonth && day.year == currentYear) day.day else -1
}

fun Context.getEvent(
    currentYear: Int,
    currentMonth: Int,
    currentDay: Int,
    events: List<EventDay>
): EventDay? {
    calendar.set(currentYear, currentMonth, currentDay)
    return events.firstOrNull {
        it.date.formatDate(this) == calendar.timeInMillis.formatDate(this)
    }
}

fun Array<String>.getWeekDay(): Array<String> {
    return if (calendar.firstDayOfWeek == Calendar.MONDAY) {
        this
    } else {
        val weekArray = this.toMutableList()
        val sunday = weekArray.last()
        weekArray.removeLast()
        weekArray.add(0, sunday)
        weekArray.toTypedArray()
    }
}