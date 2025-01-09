package com.utmaximur.money.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.money.models.MoneyStatistic
import com.utmaximur.money.store.StatisticMoneyStore.State

interface StatisticMoneyStore : Store<Nothing, State, Nothing> {

    data class State(
        val requestUi: RequestUi<List<MoneyStatistic>>
    ) {
        constructor() : this(
            requestUi = RequestUi()
        )
    }
}