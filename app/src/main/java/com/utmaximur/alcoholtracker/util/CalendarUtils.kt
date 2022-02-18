package com.utmaximur.alcoholtracker.util

import android.content.Context
import com.utmaximur.alcoholtracker.R
import java.util.*


internal fun Int.getMonthAndYearDate(context: Context, month: Int) = String.format(
    "%s  %s",
    context.resources.getStringArray(R.array.material_calendar_months_array)[month],
    this
)