package com.utmaximur.alcohol_calculator.main_screen.integration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.alcohol_calculator.AlcoholCalculatorComponent
import com.utmaximur.alcohol_calculator.main_screen.ui.AlcoholCalculatorScreen
import com.utmaximur.alcohol_calculator.models.DrinksData
import com.utmaximur.alcohol_calculator.models.Gender
import com.utmaximur.alcohol_calculator.result_dilaog.ResultDialogComponent
import com.utmaximur.alcohol_calculator.store.AlcoholCalculatorStore
import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.core.decompose.ComposeDialogComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

@Factory
internal class DefaultAlcoholCalculatorComponent(
    @InjectedParam componentContext: ComponentContext,
) : AlcoholCalculatorComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: AlcoholCalculatorStore = instanceKeeper.getStore(::get)

    private val modalNavigation = SlotNavigation<ModalConfiguration>()

    private val modalStack: Value<ChildSlot<*, ComposeComponent>> = childSlot(
        source = modalNavigation,
        serializer = ModalConfiguration.serializer(),
        handleBackButton = true,
        childFactory = ::createModal,
    )

    private fun createModal(
        modalConfig: ModalConfiguration,
        componentContext: ComponentContext,
    ): ComposeDialogComponent = when (modalConfig) {
        is ModalConfiguration.ResultDialog -> get<ResultDialogComponent> {
            parametersOf(
                componentContext,
                modalConfig.calculateResult,
                { modalNavigation.dismiss() },
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<AlcoholCalculatorStore.State> = store.stateFlow

    override fun changeHeight(height: String) =
        store.accept(AlcoholCalculatorStore.Intent.ChangeHeight(height))

    override fun changeWeight(weight: String) =
        store.accept(AlcoholCalculatorStore.Intent.ChangeWeight(weight))

    override fun changeGender(gender: Gender) =
        store.accept(AlcoholCalculatorStore.Intent.ChangeGender(gender))

    override fun onAddDrinkClick() =
        store.accept(AlcoholCalculatorStore.Intent.AddDrink)

    override fun onCalculateClick(drinksData: DrinksData) =
        store.accept(AlcoholCalculatorStore.Intent.Calculate(drinksData))

    init {
        store.labels.onEach { event ->
            when (event) {
                is AlcoholCalculatorStore.Label.ResultEvent -> modalNavigation.activate(
                    ModalConfiguration.ResultDialog(event.result)
                )
            }
        }.launchIn(coroutineScope())
    }

    @Composable
    override fun Render(modifier: Modifier) {
        AlcoholCalculatorScreen(modifier, this)
        val modals by modalStack.subscribeAsState()
        modals.child?.instance?.also { modal ->
            modal.Render(modifier)
        }
    }
}
