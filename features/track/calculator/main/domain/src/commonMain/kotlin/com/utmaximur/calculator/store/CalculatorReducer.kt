package com.utmaximur.calculator.store

import com.arkivanov.mvikotlin.core.store.Reducer

internal object CalculatorReducer : Reducer<CalculatorStore.State, Message> {

    override fun CalculatorStore.State.reduce(msg: Message) = when (msg) {
        is Message.UpdateActions -> copy(matrixItems = msg.actions)
        is Message.UpdateInput -> copy(input = msg.input)
        is Message.UpdateExpression -> copy(expression = msg.expression)
    }
}