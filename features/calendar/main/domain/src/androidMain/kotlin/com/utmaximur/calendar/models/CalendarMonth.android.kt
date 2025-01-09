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
    val displayName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        getDisplayName(
            TextStyle.FULL_STANDALONE,
            Locale.getDefault()
        )
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    // Почему-то на ру локали, некоторые месяца возвращаюся пустые.
    if (displayName.isBlank()) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, ordinal)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_STANDALONE, Locale.getDefault())?.toString() ?: name
        } else {
            name
        }
    }
    return displayName
}

