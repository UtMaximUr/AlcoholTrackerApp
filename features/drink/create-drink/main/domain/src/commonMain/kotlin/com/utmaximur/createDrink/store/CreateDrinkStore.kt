package com.utmaximur.createDrink.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.createDrink.DrinkData
import com.utmaximur.createDrink.store.CreateDrinkStore.Intent
import com.utmaximur.createDrink.store.CreateDrinkStore.Label
import com.utmaximur.createDrink.store.CreateDrinkStore.State
import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.models.Icon
import com.utmaximur.domain.models.TrackData

interface CreateDrinkStore : Store<Intent, State, Label> {

    data class State(
        val url: String,
        val icons: List<Icon>
    ) {
        constructor() : this(
            url = EMPTY_STRING,
            icons = emptyList()
        )
    }

    sealed interface Intent {
        data class SaveDrinkData(val drinkData: DrinkData) : Intent
    }

    sealed interface Label {

        data object CloseEvent: Label
    }
}