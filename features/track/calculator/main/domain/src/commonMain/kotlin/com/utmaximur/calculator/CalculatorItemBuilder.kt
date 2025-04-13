package com.utmaximur.calculator

import features.track.calculator.main.domain.Res
import features.track.calculator.main.domain.calc_0
import features.track.calculator.main.domain.calc_1
import features.track.calculator.main.domain.calc_2
import features.track.calculator.main.domain.calc_3
import features.track.calculator.main.domain.calc_4
import features.track.calculator.main.domain.calc_5
import features.track.calculator.main.domain.calc_6
import features.track.calculator.main.domain.calc_7
import features.track.calculator.main.domain.calc_8
import features.track.calculator.main.domain.calc_9
import features.track.calculator.main.domain.calc_ac
import features.track.calculator.main.domain.calc_backspace
import features.track.calculator.main.domain.calc_decimal
import features.track.calculator.main.domain.calc_divide
import features.track.calculator.main.domain.calc_equally
import features.track.calculator.main.domain.calc_minus
import features.track.calculator.main.domain.calc_multiply
import features.track.calculator.main.domain.calc_plus
import features.track.calculator.main.domain.calc_save_result
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