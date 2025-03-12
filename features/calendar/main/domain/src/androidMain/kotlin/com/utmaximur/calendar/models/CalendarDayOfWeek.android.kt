package com.utmaximur.calendar.models

import android.os.Build
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Locale

actual fun firstDayOfWeek(): DayOfWeek = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    WeekFields.of(Locale.getDefault()).firstDayOfWeek
} else {
    DayOfWeek.entries.first()
}

actual fun DayOfWeek.localized(): String {
    val locale = Locale.getDefault()
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        getDisplayName(TextStyle.SHORT, locale)
            .replaceFirstChar { it.uppercaseChar() }
    } else {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, ordinal)
        calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale) ?: name
    }
}