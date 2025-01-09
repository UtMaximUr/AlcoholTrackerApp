package com.utmaximur.drink.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.drink.StatisticDrinkComponent
import com.utmaximur.drink.store.StatisticDrinkStore
import com.utmaximur.drink.ui.StatisticDrinkScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultStatisticDrinkComponent(
    @InjectedParam componentContext: ComponentContext
) : StatisticDrinkComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: StatisticDrinkStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<StatisticDrinkStore.State> = store.stateFlow

    @Composable
    override fun Render(modifier: Modifier) = StatisticDrinkScreen(this)

}