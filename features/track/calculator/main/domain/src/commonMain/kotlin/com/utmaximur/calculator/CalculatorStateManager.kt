package com.utmaximur.calculator

import com.utmaximur.domain.EMPTY_STRING
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class CalculatorStateManager {
    private val _input = MutableStateFlow(EMPTY_STRING)
    private val _expression = MutableStateFlow(EMPTY_STRING)

    val input: StateFlow<String> = _input.asStateFlow()
    val expression: StateFlow<String> = _expression.asStateFlow()

    fun updateInput(value: String) = _input.update { value }
    fun updateExpression(value: String) = _expression.update { value }
    fun clear() {
        _input.value = EMPTY_STRING
        _expression.value = EMPTY_STRING
    }
}