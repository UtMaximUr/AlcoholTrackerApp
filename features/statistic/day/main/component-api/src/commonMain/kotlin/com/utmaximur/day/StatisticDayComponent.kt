package com.utmaximur.day

import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.day.store.StatisticDayStore
import kotlinx.coroutines.flow.StateFlow

interface StatisticDayComponent : ComposeComponent {

    val model: StateFlow<StatisticDayStore.State>

}