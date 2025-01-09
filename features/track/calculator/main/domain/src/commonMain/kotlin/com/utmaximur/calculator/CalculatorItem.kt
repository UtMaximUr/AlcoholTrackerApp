package com.utmaximur.calculator


data class CalculatorItem(
    val title: String,
    val action: CalculatorAction,
    val isMainAction: Boolean = false
)

interface CalculatorAction {
    fun execute(): CalculatorCommand
}

interface CommandHandler {
    fun handleCommand(command: CalculatorCommand)
}

sealed class CalculatorCommand {
    data class Number(val number: Int) : CalculatorCommand()
    data class MathOperation(val operation: String) : CalculatorCommand()
    data object Backspace : CalculatorCommand()
    data object Clear : CalculatorCommand()
    data object Equals : CalculatorCommand()
    data object Result : CalculatorCommand()
}

internal class BackspaceAction : CalculatorAction {
    override fun execute() = CalculatorCommand.Backspace
}

internal class PlusAction : CalculatorAction {
    override fun execute() = CalculatorCommand.MathOperation("+")
}

internal class MinusAction : CalculatorAction {
    override fun execute() = CalculatorCommand.MathOperation("-")
}

internal class MultiplyAction : CalculatorAction {
    override fun execute() = CalculatorCommand.MathOperation("*")
}

internal class DivideAction : CalculatorAction {
    override fun execute() = CalculatorCommand.MathOperation("/")
}

internal class ClearAction : CalculatorAction {
    override fun execute() = CalculatorCommand.Clear
}

internal class ResultAction : CalculatorAction {
    override fun execute() = CalculatorCommand.Result
}

internal class EqualsAction : CalculatorAction {
    override fun execute() = CalculatorCommand.Equals
}

internal class DecimalAction : CalculatorAction {
    override fun execute() = CalculatorCommand.MathOperation(",")
}

internal class NumberAction(private val number: Int) : CalculatorAction {
    override fun execute() = CalculatorCommand.Number(number)
}
