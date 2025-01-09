package com.utmaximur.calculator.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.github.murzagalin.evaluator.Evaluator
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.calculator.CalculatorCommand
import com.utmaximur.calculator.CalculatorItemBuilder
import com.utmaximur.calculator.MatrixItems
import com.utmaximur.calculator.analytic_events.OpenScreenEvent
import com.utmaximur.calculator.store.CalculatorStore.Intent
import com.utmaximur.calculator.store.CalculatorStore.Label
import com.utmaximur.calculator.store.CalculatorStore.State
import com.utmaximur.domain.calculator.CalculatorProviderData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


internal sealed interface Message {
    data class UpdateActions(val actions: MatrixItems) : Message
    data class UpdateInput(val input: String) : Message
    data class UpdateExpression(val expression: String) : Message
}

internal class CalculatorExecutor(
    private val evaluator: Evaluator,
    private val providerData: CalculatorProviderData,
    private val itemBuilder: CalculatorItemBuilder,
    private val analyticsManager: AnalyticsManager
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {

    private val inputValue = MutableStateFlow(String())
    private val expressionValue = MutableStateFlow(String())

    override fun executeAction(action: Unit) {
        scope.launch { analyticsManager.trackEvent(OpenScreenEvent()) }
        bindCalculateState()
    }

    override fun executeIntent(intent: Intent): Unit =
        when (intent) {
            is Intent.Command -> handleCommand(intent.command)
        }

    private fun bindCalculateState() {
        scope.launch {
            val matrixCalculatorItems = itemBuilder.build()
            dispatch(Message.UpdateActions(matrixCalculatorItems))
        }
        inputValue
            .onEach { value -> dispatch(Message.UpdateInput(value)) }
            .launchIn(scope)
        expressionValue
            .onEach { value -> dispatch(Message.UpdateExpression(value)) }
            .launchIn(scope)
    }

    private fun handleCommand(command: CalculatorCommand) {
        when (command) {
            CalculatorCommand.Backspace -> inputValue.update { it.dropLast(1) }
            CalculatorCommand.Clear -> {
                expressionValue.update { String() }
                inputValue.update { String() }
            }

            is CalculatorCommand.MathOperation -> inputValue.update { it + command.operation }
            is CalculatorCommand.Equals -> {
                expressionValue.update { inputValue.value }
                inputValue.update { calculate() }
            }

            CalculatorCommand.Result -> scope.launch {
                val result = state().input.toFloatOrNull()
                providerData.sendData(result)
                publish(Label.CloseEvent)
            }

            is CalculatorCommand.Number -> inputValue.update { it + command.number }
        }
    }

    private fun calculate() = evaluator
        .evaluateDouble(inputValue.value)
        .toFloat()
        .toString()
        .removeSuffix(".0")
}