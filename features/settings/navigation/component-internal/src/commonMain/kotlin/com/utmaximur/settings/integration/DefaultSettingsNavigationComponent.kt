package com.utmaximur.settings.integration

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
import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.currency.CurrencyComponent
import com.utmaximur.settings.SettingsComponent
import com.utmaximur.settings.SettingsNavigationComponent
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parameterArrayOf

@Factory
internal class DefaultSettingsNavigationComponent(
    @InjectedParam componentContext: ComponentContext
) : SettingsNavigationComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val navigation = StackNavigation<SettingsNavigationConfiguration>()

    private val modalNavigation = SlotNavigation<ModalConfiguration>()

    private val closeDialog = {
        modalNavigation.dismiss()
    }

    private val modalStack: Value<ChildSlot<*, ComposeComponent>> =
        childSlot(
            source = modalNavigation,
            serializer = ModalConfiguration.serializer(),
            handleBackButton = true,
            childFactory = ::createModal
        )

    private fun createModal(
        modalConfiguration: ModalConfiguration,
        componentContext: ComponentContext
    ): ComposeComponent =
        when (modalConfiguration) {
            is ModalConfiguration.Currency -> get<CurrencyComponent> {
                parameterArrayOf(
                    componentContext,
                    closeDialog
                )
            }
        }

    private val stack = childStack(
        source = navigation,
        serializer = SettingsNavigationConfiguration.serializer(),
        initialConfiguration = SettingsNavigationConfiguration.Main,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        configuration: SettingsNavigationConfiguration,
        componentContext: ComponentContext
    ): ComposeComponent = when (configuration) {
        is SettingsNavigationConfiguration.Main -> get<SettingsComponent> {
            parameterArrayOf(
                componentContext,
                ::onSettingsOutput
            )
        }
    }

    private fun onSettingsOutput(output: SettingsComponent.Output): Unit = when (output) {
        is SettingsComponent.Output.OpenSelectCurrencyDialog ->
            modalNavigation.activate(ModalConfiguration.Currency)
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
        modals.child?.instance?.Render(modifier)
    }
}