package com.utmaximur.calculator


internal typealias MatrixItems = List<List<CalculatorItem>>

internal class CalculatorItemBuilder {
    fun build(): MatrixItems = listOf(
        createTopOperationRow(),
        createNumberRow(7, 8, 9, operationFactory = ::MultiplyAction),
        createNumberRow(4, 5, 6, operationFactory = ::MinusAction),
        createNumberRow(1, 2, 3, operationFactory = ::PlusAction),
        createBottomRow(),
    )

    private fun createTopOperationRow() = listOf(
        actionItem(AC, ClearAction()),
        actionItem(BACKSPACE, BackspaceAction()),
        highlightedActionItem(DIVIDE, ::DivideAction)
    )

    private fun createNumberRow(
        vararg numbers: Int,
        operationFactory: () -> CalculatorAction
    ): List<CalculatorItem> = numbers.map {
        numberItem(it.toString(), NumberAction(it))
    } + highlightedActionItem(
        getOperationSymbol(operationFactory),
        operationFactory
    )

    private fun createBottomRow() = listOf(
        numberItem("0", NumberAction(0)),
        actionItem(DECIMAL, DecimalAction()),
        highlightedActionItem(EQUALLY, ::EqualsAction)
    )

    // region Helper methods
    private fun getOperationSymbol(factory: () -> CalculatorAction): String =
        when (factory()) {
            is DivideAction -> DIVIDE
            is MultiplyAction -> MULTIPLY
            is MinusAction -> MINUS
            is PlusAction -> PLUS
            else -> ""
        }

    private fun actionItem(
        label: String,
        action: CalculatorAction
    ) = CalculatorItem(label, action)

    private fun numberItem(
        label: String,
        number: NumberAction
    ) = CalculatorItem(label, number)

    private fun highlightedActionItem(
        label: String,
        actionFactory: () -> CalculatorAction
    ) = CalculatorItem(label, actionFactory(), isHighlighted = true)
    // endregion

    companion object {
        private const val AC = "AC"
        private const val BACKSPACE = "⌫"
        private const val DIVIDE = "÷"
        private const val MULTIPLY = "x"
        private const val MINUS = "-"
        private const val PLUS = "+"
        private const val DECIMAL = ","
        private const val EQUALLY = "="
    }
}