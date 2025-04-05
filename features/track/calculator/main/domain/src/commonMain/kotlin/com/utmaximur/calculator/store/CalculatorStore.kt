package com.utmaximur.calculator.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.calculator.CalculatorCommand
import com.utmaximur.calculator.MatrixItems
import com.utmaximur.calculator.store.CalculatorStore.Intent
import com.utmaximur.calculator.store.CalculatorStore.Label
import com.utmaximur.calculator.store.CalculatorStore.State
import com.utmaximur.domain.EMPTY_STRING

interface CalculatorStore : Store<Intent, State, Label> {

    data class State(
        val matrixItems: MatrixItems,
        val input: String,
        val expression: String
    ) {
        constructor() : this(
            matrixItems = emptyList(),
            input = EMPTY_STRING,
            expression = EMPTY_STRING
        )
    }

    sealed interface Intent {

        data class Command(val command: CalculatorCommand) : Intent

    }

    sealed interface Label {

        data object CloseEvent : Label
    }
}