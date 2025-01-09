package com.utmaximur.day.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.day.models.DayStatistic
import com.utmaximur.day.store.StatisticDayStore.*

interface StatisticDayStore : Store<Nothing, State, Nothing> {

    data class State(
        val requestUi: RequestUi<List<DayStatistic>>
    ) {
        constructor() : this(
            requestUi = RequestUi()
        )
    }
}