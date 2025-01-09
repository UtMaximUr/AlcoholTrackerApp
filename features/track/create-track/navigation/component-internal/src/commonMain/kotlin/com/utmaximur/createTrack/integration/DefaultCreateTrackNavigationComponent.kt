package com.utmaximur.createTrack.integration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.utmaximur.calculator.CalculatorComponent
import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.core.decompose.ComposeDialogComponent
import com.utmaximur.createDrink.CreateDrinkNavigationComponent
import com.utmaximur.createTrack.CreateTrackComponent
import com.utmaximur.createTrack.CreateTrackNavigationComponent
import com.utmaximur.currency.CurrencyComponent
import com.utmaximur.datePicker.DatePickerComponent
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parameterArrayOf
import org.koin.core.parameter.parametersOf

@Factory
internal class DefaultCreateTrackNavigationComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val back: () -> Unit
) : CreateTrackNavigationComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val navigation = StackNavigation<Configuration>()

    private val stack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.CreateTrackScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): ComposeComponent =
        when (configuration) {
            is Configuration.CreateTrackScreen -> get<CreateTrackComponent> {
                parameterArrayOf(
                    componentContext,
                    ::onCreateTrackOutput
                )
            }

            Configuration.CreateDrinkScreen -> get<CreateDrinkNavigationComponent> {
                parameterArrayOf(
                    componentContext,
                    { navigation.pop() }
                )
            }
        }

    private val modalNavigation = SlotNavigation<ModalConfiguration>()

    private val modalStack: Value<ChildSlot<*, ComposeComponent>> = childSlot(
        source = modalNavigation,
        serializer = ModalConfiguration.serializer(),
        handleBackButton = true,
        childFactory = ::createModal
    )

    private fun createModal(
        modalConfig: ModalConfiguration, componentContext: ComponentContext
    ): ComposeDialogComponent = when (modalConfig) {
        ModalConfiguration.CalculatorDialog -> get<CalculatorComponent> {
            parametersOf(
                componentContext,
                { modalNavigation.dismiss() }
            )
        }

        ModalConfiguration.CurrencyDialog -> get<CurrencyComponent> {
            parametersOf(
                componentContext,
                { modalNavigation.dismiss() }
            )
        }

        is ModalConfiguration.DatePickerDialog -> get<DatePickerComponent> {
            parametersOf(
                componentContext,
                modalConfig.selectedDate,
                { modalNavigation.dismiss() }
            )
        }
    }

    private fun onCreateTrackOutput(output: CreateTrackComponent.Output) = when (output) {
        CreateTrackComponent.Output.NavigateBack -> back()
        CreateTrackComponent.Output.OpenCalculatorDialog -> modalNavigation.activate(
            ModalConfiguration.CalculatorDialog
        )

        CreateTrackComponent.Output.OpenCurrencyDialog -> modalNavigation.activate(
            ModalConfiguration.CurrencyDialog
        )

        is CreateTrackComponent.Output.OpenDatePickerDialog -> modalNavigation.activate(
            ModalConfiguration.DatePickerDialog(output.selectedDate)
        )

        CreateTrackComponent.Output.NavigateToCreateDrink -> navigation.pushNew(
            Configuration.CreateDrinkScreen
        )
    }

    @Composable
    override fun Render(modifier: Modifier) {
        Children(
            stack = stack,
            animation = stackAnimation(slide())
        ) { child ->
            child.instance.Render(modifier)
        }
        val modals by modalStack.subscribeAsState()
        modals.child?.instance?.also { modal ->
            modal.Render(modifier)
        }
    }
}