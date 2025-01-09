package com.utmaximur.drink

import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.drink.store.StatisticDrinkStore
import kotlinx.coroutines.flow.StateFlow

interface StatisticDrinkComponent : ComposeComponent {

    val model: StateFlow<StatisticDrinkStore.State>

}