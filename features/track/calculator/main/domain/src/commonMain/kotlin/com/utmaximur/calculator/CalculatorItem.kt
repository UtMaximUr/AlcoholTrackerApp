package com.utmaximur.calculator

private const val DECIMAL_ACTION = ","
private const val DIVIDE_ACTION = "/"
private const val MULTIPLY_ACTION = "*"
private const val PLUS_ACTION = "+"
private const val MINUS_ACTION = "-"

data class CalculatorItem(
    val title: String,
    val action: CalculatorAction,
    val isHighlighted: Boolean = false
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
}

internal class BackspaceAction : CalculatorAction {
    override fun execute() = CalculatorCommand.Backspace
}

internal class PlusAction : CalculatorAction {
    override fun execute() = CalculatorCommand.MathOperation(PLUS_ACTION)
}

internal class MinusAction : CalculatorAction {
    override fun execute() = CalculatorCommand.MathOperation(MINUS_ACTION)
}

internal class MultiplyAction : CalculatorAction {
    override fun execute() = CalculatorCommand.MathOperation(MULTIPLY_ACTION)
}

internal class DivideAction : CalculatorAction {
    override fun execute() = CalculatorCommand.MathOperation(DIVIDE_ACTION)
}

internal class ClearAction : CalculatorAction {
    override fun execute() = CalculatorCommand.Clear
}

internal class EqualsAction : CalculatorAction {
    override fun execute() = CalculatorCommand.Equals
}

internal class DecimalAction : CalculatorAction {
    override fun execute() = CalculatorCommand.MathOperation(DECIMAL_ACTION)
}

internal class NumberAction(private val number: Int) : CalculatorAction {
    override fun execute() = CalculatorCommand.Number(number)
}
