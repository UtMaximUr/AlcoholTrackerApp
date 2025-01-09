package com.utmaximur.money.models

data class MoneyStatistic(
    val statisticPeriod: StatisticPeriod,
    val moneyAmount: String,
    val currency: String
)