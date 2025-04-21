package com.utmaximur.sortingDrinks.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.utmaximur.sortingDrinks.store.SortingDrinksStore.State

internal object SortingDrinksReducer : Reducer<State, Message> {

    override fun State.reduce(msg: Message) = when (msg) {
        is Message.UpdateSortedDrinks -> copy(sortedDrinks = msg.sortedDrinks)
    }
}