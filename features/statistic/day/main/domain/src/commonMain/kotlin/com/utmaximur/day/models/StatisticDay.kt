package com.utmaximur.day.models

enum class StatisticDay {
    COUNT_DAYS,
    COUNT_DAYS_NO_DRINK;

    val raw: String get() = name.lowercase()
}