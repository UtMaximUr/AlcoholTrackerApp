package com.utmaximur.calculator

import calculator.domain.resources.Res
import calculator.domain.resources.calc_0
import calculator.domain.resources.calc_1
import calculator.domain.resources.calc_2
import calculator.domain.resources.calc_3
import calculator.domain.resources.calc_4
import calculator.domain.resources.calc_5
import calculator.domain.resources.calc_6
import calculator.domain.resources.calc_7
import calculator.domain.resources.calc_8
import calculator.domain.resources.calc_9
import calculator.domain.resources.calc_ac
import calculator.domain.resources.calc_divide
import calculator.domain.resources.calc_equally
import calculator.domain.resources.calc_minus
import calculator.domain.resources.calc_multiply
import calculator.domain.resources.calc_plus
import calculator.domain.resources.calc_backspace
import calculator.domain.resources.calc_decimal
import calculator.domain.resources.calc_save_result
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.koin.core.annotation.Factory

internal typealias MatrixItems = List<List<CalculatorItem>>

@Factory
internal class CalculatorItemBuilder {

    suspend fun build(): MatrixItems {
        return listOf(
            listOf(
                createWithResources(Res.string.calc_ac, ClearAction()),
                createWithResources(Res.string.calc_backspace, BackspaceAction()),
                createWithResources(Res.string.calc_divide, DivideAction(), true)
            ),
            listOf(
                createWithResources(Res.string.calc_7, NumberAction(7)),
                createWithResources(Res.string.calc_8, NumberAction(8)),
                createWithResources(Res.string.calc_9, NumberAction(9)),
                createWithResources(Res.string.calc_multiply, MultiplyAction(), true)
            ),
            listOf(
                createWithResources(Res.string.calc_4, NumberAction(4)),
                createWithResources(Res.string.calc_5, NumberAction(5)),
                createWithResources(Res.string.calc_6, NumberAction(6)),
                createWithResources(Res.string.calc_minus, MinusAction(), true)
            ),
            listOf(
                createWithResources(Res.string.calc_1, NumberAction(1)),
                createWithResources(Res.string.calc_2, NumberAction(2)),
                createWithResources(Res.string.calc_3, NumberAction(3)),
                createWithResources(Res.string.calc_plus, PlusAction(), true)
            ),
            listOf(
                createWithResources(Res.string.calc_0, NumberAction(0)),
                createWithResources(Res.string.calc_decimal, DecimalAction()),
                createWithResources(Res.string.calc_equally, EqualsAction(), true)
            ),
            listOf(
                createWithResources(Res.string.calc_save_result, ResultAction(), true),
            )
        )
    }

    private suspend fun createWithResources(
        resource: StringResource,
        action: CalculatorAction,
        isMainAction: Boolean = false
    ) = CalculatorItem(
        title = getString(resource),
        action = action,
        isMainAction = isMainAction
    )
}