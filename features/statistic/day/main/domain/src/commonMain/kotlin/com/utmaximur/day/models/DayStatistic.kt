package com.utmaximur.day.models

import com.utmaximur.domain.ZERO_VALUE

data class DayStatistic(
    val statisticDay: StatisticDay,
    val countsDaysInYear: Int = ZERO_VALUE,
    val countsDays: Int
)