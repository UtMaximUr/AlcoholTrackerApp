package com.utmaximur.calendar.models


import android.os.Build
import kotlinx.datetime.Month
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale


actual fun CalendarMonth.localized(): String {
    return "${month.localized()} $year"
}

actual fun Month.localized(): String {
    val locale = Locale.getDefault()
    val displayName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        getDisplayName(TextStyle.FULL_STANDALONE, locale)
    } else {
        getDisplayNameWithCalendar(locale, ordinal)
    }
    // Почему-то на ру локали, некоторые месяца возвращаюся пустые.
    if (displayName.isNullOrBlank()) {
        return getDisplayNameWithCalendar(locale, ordinal) ?: name
    }
    return displayName
}

private fun getDisplayNameWithCalendar(locale: Locale, ordinal: Int): String? {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.MONTH, ordinal)
    return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)
}

