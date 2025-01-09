package com.utmaximur.money.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.money.StatisticMoneyComponent
import com.utmaximur.money.store.StatisticMoneyStore
import com.utmaximur.money.ui.StatisticMoneyScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultStatisticMoneyComponent(
    @InjectedParam componentContext: ComponentContext
) : StatisticMoneyComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: StatisticMoneyStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<StatisticMoneyStore.State> = store.stateFlow

    @Composable
    override fun Render(modifier: Modifier) = StatisticMoneyScreen(this)

}