package com.utmaximur.calendar.models

import android.os.Build
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

actual fun firstDayOfWeek(): DayOfWeek = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    WeekFields.of(Locale.getDefault()).firstDayOfWeek!!
} else {
    TODO("VERSION.SDK_INT < O")
}

actual fun DayOfWeek.localized(): String =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        getDisplayName(TextStyle.SHORT, Locale.getDefault())
            .replaceFirstChar { it.uppercaseChar() }
    } else {
        TODO("VERSION.SDK_INT < O")
    }
