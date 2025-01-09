package com.utmaximur.calculator.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.calculator.CalculatorCommand
import com.utmaximur.calculator.CalculatorComponent
import com.utmaximur.calculator.store.CalculatorStore
import com.utmaximur.calculator.ui.CalculatorScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultCalculatorComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val closeDialog: () -> Unit
) : CalculatorComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: CalculatorStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<CalculatorStore.State> = store.stateFlow

    override fun dismiss() = closeDialog()

    override fun handleCommand(command: CalculatorCommand) =
        store.accept(CalculatorStore.Intent.Command(command))

    init {
        store.labels.onEach { event ->
            when (event) {
                is CalculatorStore.Label.CloseEvent -> dismiss()
            }
        }.launchIn(coroutineScope())
    }

    @Composable
    override fun Render(modifier: Modifier) = CalculatorScreen(this)

}