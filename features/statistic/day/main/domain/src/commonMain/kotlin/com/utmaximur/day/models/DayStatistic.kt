package com.utmaximur.day.models

data class DayStatistic(
    val statisticDay: StatisticDay,
    val countsDaysInYear: Int = 0,
    val countsDays: Int
)