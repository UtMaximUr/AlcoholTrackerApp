package com.utmaximur.money

import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.money.store.StatisticMoneyStore
import kotlinx.coroutines.flow.StateFlow

interface StatisticMoneyComponent : ComposeComponent {

    val model: StateFlow<StatisticMoneyStore.State>

}