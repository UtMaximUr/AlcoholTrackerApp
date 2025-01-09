package com.utmaximur.detailTrack.integration

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
import com.arkivanov.decompose.value.Value
import com.utmaximur.calculator.CalculatorComponent
import com.utmaximur.confirmDialog.ConfirmDialogComponent
import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.core.decompose.ComposeDialogComponent
import com.utmaximur.datePicker.DatePickerComponent
import com.utmaximur.detailTrack.DetailTrackComponent
import com.utmaximur.detailTrack.DetailTrackNavigationComponent
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parameterArrayOf
import org.koin.core.parameter.parametersOf

@Factory
internal class DefaultDetailTrackNavigationComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val trackId: Long,
    @InjectedParam private val back: () -> Unit
) : DetailTrackNavigationComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val navigation = StackNavigation<Configuration>()

    private val stack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.DetailTrackScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): ComposeComponent =
        when (configuration) {
            is Configuration.DetailTrackScreen -> get<DetailTrackComponent> {
                parameterArrayOf(
                    componentContext,
                    trackId,
                    ::onDetailTrackOutput
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

        is ModalConfiguration.DatePickerDialog -> get<DatePickerComponent> {
            parametersOf(
                componentContext,
                modalConfig.selectedDate,
                { modalNavigation.dismiss() }
            )
        }

        is ModalConfiguration.ConfirmDialog -> get<ConfirmDialogComponent> {
            parametersOf(
                componentContext,
                modalConfig.trackId,
                { modalNavigation.dismiss() }
            )
        }
    }

    private fun onDetailTrackOutput(output: DetailTrackComponent.Output) = when (output) {
        DetailTrackComponent.Output.NavigateBack -> back()
        DetailTrackComponent.Output.OpenCalculatorDialog -> modalNavigation.activate(
            ModalConfiguration.CalculatorDialog
        )

        is DetailTrackComponent.Output.OpenDatePickerDialog -> modalNavigation.activate(
            ModalConfiguration.DatePickerDialog(output.selectedDate)
        )

        is DetailTrackComponent.Output.OpenConfirmDialog -> modalNavigation.activate(
            ModalConfiguration.ConfirmDialog(output.trackId)
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