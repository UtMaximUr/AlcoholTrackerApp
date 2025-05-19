package com.utmaximur.calculator.ui
//
//import com.utmaximur.calculator.BackspaceAction
//import com.utmaximur.calculator.CalculatorAction
//import com.utmaximur.calculator.CalculatorItem
//import com.utmaximur.calculator.ClearAction
//import com.utmaximur.calculator.DecimalAction
//import com.utmaximur.calculator.DivideAction
//import com.utmaximur.calculator.EqualsAction
//import com.utmaximur.calculator.MinusAction
//import com.utmaximur.calculator.MultiplyAction
//import com.utmaximur.calculator.NumberAction
//import com.utmaximur.calculator.PlusAction
//import com.utmaximur.calculator.ResultAction
//import features.track.calculator.main.Res
//import features.track.calculator.main.calc_0
//import features.track.calculator.main.calc_ac
//import features.track.calculator.main.calc_backspace
//import features.track.calculator.main.calc_decimal
//import features.track.calculator.main.calc_divide
//import features.track.calculator.main.calc_equally
//import features.track.calculator.main.calc_minus
//import features.track.calculator.main.calc_multiply
//import features.track.calculator.main.calc_plus
//import features.track.calculator.main.calc_save_result
//import org.jetbrains.compose.resources.getString
//
//internal typealias MatrixItems = List<List<CalculatorItem>>
//
//internal class CalculatorItemBuilder {
//    suspend fun build(): MatrixItems = listOf(
//        createTopOperationRow(),
//        createNumberRow(7, 8, 9, operationFactory = ::MultiplyAction),
//        createNumberRow(4, 5, 6, operationFactory = ::MinusAction),
//        createNumberRow(1, 2, 3, operationFactory = ::PlusAction),
//        createBottomRow(),
//        createSpecialOperationsRow()
//    )
//
//    private suspend fun createTopOperationRow() = listOf(
//        actionItem(getString(Res.string.calc_ac), ClearAction()),
//        actionItem(getString(Res.string.calc_backspace), BackspaceAction()),
//        highlightedActionItem(getString(Res.string.calc_divide), ::DivideAction)
//    )
//
//    private suspend fun createNumberRow(
//        vararg numbers: Int,
//        operationFactory: () -> CalculatorAction
//    ): List<CalculatorItem> = numbers.map {
//        numberItem(it.toString(), NumberAction(it))
//    } + highlightedActionItem(
//        getOperationSymbol(operationFactory),
//        operationFactory
//    )
//
//    private suspend fun createBottomRow() = listOf(
//        numberItem(getString(Res.string.calc_0), NumberAction(0)),
//        actionItem(getString(Res.string.calc_decimal), DecimalAction()),
//        highlightedActionItem(getString(Res.string.calc_equally), ::EqualsAction)
//    )
//
//    private suspend fun createSpecialOperationsRow() = listOf(
//        highlightedActionItem(getString(Res.string.calc_save_result), ::ResultAction)
//    )
//
//    // region Helper methods
//    private suspend fun getOperationSymbol(factory: () -> CalculatorAction): String =
//        when (factory()) {
//            is DivideAction -> getString(Res.string.calc_divide)
//            is MultiplyAction -> getString(Res.string.calc_multiply)
//            is MinusAction -> getString(Res.string.calc_minus)
//            is PlusAction -> getString(Res.string.calc_plus)
//            else -> ""
//        }
//
//    private fun actionItem(
//        label: String,
//        action: CalculatorAction
//    ) = CalculatorItem(label, action)
//
//    private fun numberItem(
//        label: String,
//        number: NumberAction
//    ) = CalculatorItem(label, number)
//
//    private fun highlightedActionItem(
//        label: String,
//        actionFactory: () -> CalculatorAction
//    ) = CalculatorItem(label, actionFactory(), isHighlighted = true)
//    // endregion
//}