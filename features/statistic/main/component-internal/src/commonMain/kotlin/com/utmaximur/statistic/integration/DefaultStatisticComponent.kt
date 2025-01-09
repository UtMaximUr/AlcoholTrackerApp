package com.utmaximur.statistic.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.utmaximur.day.StatisticDayComponent
import com.utmaximur.drink.StatisticDrinkComponent
import com.utmaximur.money.StatisticMoneyComponent
import com.utmaximur.statistic.StatisticComponent
import com.utmaximur.statistic.ui.StatisticScreen
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parameterArrayOf

@Factory
internal class DefaultStatisticComponent(
    @InjectedParam componentContext: ComponentContext
) : StatisticComponent,
    ComponentContext by componentContext,
    KoinComponent {

    override val statisticMoneyComponent: StatisticMoneyComponent by lazy {
        get {
            parameterArrayOf(
                childContext(StatisticMoneyComponent::class.simpleName.orEmpty())
            )
        }
    }

    override val statisticDayComponent: StatisticDayComponent by lazy {
        get {
            parameterArrayOf(
                childContext(StatisticDayComponent::class.simpleName.orEmpty())
            )
        }
    }

    override val statisticDrinkComponent: StatisticDrinkComponent by lazy {
        get {
            parameterArrayOf(
                childContext(StatisticDrinkComponent::class.simpleName.orEmpty())
            )
        }
    }

    @Composable
    override fun Render(modifier: Modifier) = StatisticScreen(modifier, this)

}