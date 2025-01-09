package com.utmaximur.day.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.day.StatisticDayComponent
import com.utmaximur.day.store.StatisticDayStore
import com.utmaximur.day.ui.StatisticDayScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultStatisticDayComponent(
    @InjectedParam componentContext: ComponentContext
) : StatisticDayComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: StatisticDayStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<StatisticDayStore.State> = store.stateFlow

    @Composable
    override fun Render(modifier: Modifier) = StatisticDayScreen(this)

}