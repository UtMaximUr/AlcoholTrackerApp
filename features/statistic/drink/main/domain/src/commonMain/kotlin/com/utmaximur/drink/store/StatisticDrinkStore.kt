package com.utmaximur.drink.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.drink.model.DrinkStatistic
import com.utmaximur.drink.store.StatisticDrinkStore.State


interface StatisticDrinkStore : Store<Nothing, State, Nothing> {

    data class State(
        val requestUi: RequestUi<List<DrinkStatistic>>
    ) {
        constructor() : this(
            requestUi = RequestUi()
        )
    }
}