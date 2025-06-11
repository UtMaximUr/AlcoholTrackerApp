package com.utmaximur.widget.mapper

import com.utmaximur.money.models.MoneyStatistic
import com.utmaximur.money.models.StatisticPeriod
import com.utmaximur.widget.model.MoneyStatisticUi
import features.appwidget.component.R

internal fun MoneyStatistic.toMoneyStatisticUi() = MoneyStatisticUi(
    titleResId = this.getTitle(),
    amount = this.moneyAmount,
    currency = this.currency,
)

internal fun MoneyStatistic.getTitle() = when(this.statisticPeriod) {
    StatisticPeriod.WEEK_PERIOD -> R.string.title_week
    StatisticPeriod.MONTH_PERIOD -> R.string.title_month
    StatisticPeriod.YEAR_PERIOD -> R.string.title_year
}