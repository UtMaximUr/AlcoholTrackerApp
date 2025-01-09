package com.utmaximur.createDrink.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.utmaximur.createDrink.store.CreateDrinkStore.State

internal object CreateDrinkReducer : Reducer<State, Message> {

    override fun State.reduce(msg: Message) = when (msg) {
        is Message.UpdateImageUri -> copy(url = msg.path)
        is Message.UpdateIcons -> copy(icons = msg.icons)
    }
}