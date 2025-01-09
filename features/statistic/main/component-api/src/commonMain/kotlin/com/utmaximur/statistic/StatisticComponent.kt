package com.utmaximur.statistic

import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.day.StatisticDayComponent
import com.utmaximur.drink.StatisticDrinkComponent
import com.utmaximur.money.StatisticMoneyComponent

interface StatisticComponent : ComposeComponent {

    val statisticMoneyComponent: StatisticMoneyComponent

    val statisticDayComponent: StatisticDayComponent

    val statisticDrinkComponent: StatisticDrinkComponent
}