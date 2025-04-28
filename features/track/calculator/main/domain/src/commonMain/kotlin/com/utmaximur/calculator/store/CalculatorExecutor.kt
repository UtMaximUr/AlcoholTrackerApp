package com.utmaximur.calculator.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.calculator.CalculatorCommand
import com.utmaximur.calculator.CalculatorItemBuilder
import com.utmaximur.calculator.CalculatorStateManager
import com.utmaximur.calculator.MatrixItems
import com.utmaximur.calculator.analytic_events.OpenScreenEvent
import com.utmaximur.calculator.interactor.CalculatorOperations
import com.utmaximur.calculator.store.CalculatorStore.Intent
import com.utmaximur.calculator.store.CalculatorStore.Label
import com.utmaximur.calculator.store.CalculatorStore.State
import com.utmaximur.domain.calculator.CalculatorProviderData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


internal sealed interface Message {
    data class UpdateActions(val actions: MatrixItems) : Message
    data class UpdateInput(val input: String) : Message
    data class UpdateExpression(val expression: String) : Message
}

internal class CalculatorExecutor(
    private val calculatorOperationsInteractor: CalculatorOperations,
    private val providerData: CalculatorProviderData,
    private val analyticsManager: AnalyticsManager,
    private val itemBuilder: CalculatorItemBuilder = CalculatorItemBuilder(),
    private val stateManager: CalculatorStateManager = CalculatorStateManager()
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {

    override fun executeAction(action: Unit) {
        trackScreenOpen()
        setupStateObservers()
        initializeCalculatorItems()
    }

    override fun executeIntent(intent: Intent): Unit =
        when (intent) {
            is Intent.Command -> handleCommand(intent.command)
        }

    private fun trackScreenOpen() = scope.launch {
        analyticsManager.trackEvent(OpenScreenEvent())
    }

    private fun setupStateObservers() {
        stateManager.input
            .onEach { value -> dispatch(Message.UpdateInput(value)) }
            .launchIn(scope)
        stateManager.expression
            .onEach { value -> dispatch(Message.UpdateExpression(value)) }
            .launchIn(scope)
    }

    private fun initializeCalculatorItems() {
        scope.launch {
            val matrixCalculatorItems = itemBuilder.build()
            dispatch(Message.UpdateActions(matrixCalculatorItems))
        }
    }

    private fun handleCommand(command: CalculatorCommand) {
        when (command) {
            CalculatorCommand.Backspace -> handleBackspace()
            CalculatorCommand.Clear -> handleClear()
            is CalculatorCommand.MathOperation -> handleMathOperation(command.operation)
            is CalculatorCommand.Equals -> handleEquals()
            CalculatorCommand.Result -> handleResult()
            is CalculatorCommand.Number -> handleNumber(command.number)
        }
    }

    private fun handleBackspace() =
        stateManager.updateInput(stateManager.input.value.dropLast(1))

    private fun handleClear() = stateManager.clear()

    private fun handleMathOperation(operation: String) =
        stateManager.updateInput(stateManager.input.value + operation)

    private fun handleEquals() {
        stateManager.updateExpression(stateManager.input.value)
        scope.launch {
            calculatorOperationsInteractor.invoke(stateManager.input.value)
                .onSuccess { result ->
                    stateManager.updateInput(result)
                }
                .onFailure { error ->
                    println("Calculation error: ${error.message}")
                }
        }
    }

    private fun handleResult() = scope.launch {
        val result = stateManager.input.value.toFloatOrNull()
        providerData.sendData(result)
        publish(Label.CloseEvent)
    }

    private fun handleNumber(number: Int) =
        stateManager.updateInput(stateManager.input.value + number)
}