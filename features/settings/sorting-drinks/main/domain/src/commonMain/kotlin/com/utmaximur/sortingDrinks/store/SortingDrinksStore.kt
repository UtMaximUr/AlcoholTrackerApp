package com.utmaximur.sortingDrinks.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.domain.sortingDrinks.SortingDrink
import com.utmaximur.sortingDrinks.store.SortingDrinksStore.Intent
import com.utmaximur.sortingDrinks.store.SortingDrinksStore.Label
import com.utmaximur.sortingDrinks.store.SortingDrinksStore.State

interface SortingDrinksStore : Store<Intent, State, Label> {

    data class State(
        val sortedDrinks: List<SortingDrink>,
    ) {
        constructor() : this(
            sortedDrinks = emptyList(),
        )
    }

    sealed interface Intent {

        data object SaveSortingDrinks : Intent

        data class Move(val from: Int, val to: Int) : Intent

    }

    sealed interface Label {

        data object CloseEvent : Label
    }
}