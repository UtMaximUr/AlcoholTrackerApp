package com.utmaximur.money.models

import kotlinx.datetime.DateTimeUnit

enum class StatisticPeriod(
    val dateTimeUnit: DateTimeUnit
) {
    WEEK_PERIOD(DateTimeUnit.WEEK),
    MONTH_PERIOD(DateTimeUnit.MONTH),
    YEAR_PERIOD(DateTimeUnit.YEAR);

    val raw: String get() = name.lowercase()
}