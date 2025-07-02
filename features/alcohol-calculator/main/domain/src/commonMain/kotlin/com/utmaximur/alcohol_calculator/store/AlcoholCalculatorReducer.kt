package com.utmaximur.alcohol_calculator.store

import com.arkivanov.mvikotlin.core.store.Reducer

internal object AlcoholCalculatorReducer : Reducer<AlcoholCalculatorStore.State, Message> {

    override fun AlcoholCalculatorStore.State.reduce(msg: Message) = when (msg) {
        is Message.UpdateGender -> copy(gender = msg.gender)
        is Message.UpdateHeight -> copy(height = msg.height)
        is Message.UpdateWeight -> copy(weight = msg.weight)
        is Message.UpdateDrinksCount -> copy(drinksCount = msg.drinksCount)
    }
}