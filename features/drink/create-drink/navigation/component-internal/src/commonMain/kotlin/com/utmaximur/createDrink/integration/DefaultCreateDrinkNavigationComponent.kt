package com.utmaximur.createDrink.integration

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
import com.utmaximur.actions.ActionsImageComponent
import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.core.decompose.ComposeDialogComponent
import com.utmaximur.createDrink.CreateDrinkComponent
import com.utmaximur.createDrink.CreateDrinkNavigationComponent
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parameterArrayOf
import org.koin.core.parameter.parametersOf

@Factory
internal class DefaultCreateDrinkNavigationComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val back: () -> Unit
) : CreateDrinkNavigationComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val navigation = StackNavigation<Configuration>()

    private val stack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.CreateDrinkScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): ComposeComponent =
        when (configuration) {
            is Configuration.CreateDrinkScreen -> get<CreateDrinkComponent> {
                parameterArrayOf(
                    componentContext,
                    ::onCreateDrinkOutput
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
        ModalConfiguration.ImageActionsDialog -> get<ActionsImageComponent> {
            parametersOf(
                componentContext,
                { modalNavigation.dismiss() }
            )
        }
    }

    private fun onCreateDrinkOutput(output: CreateDrinkComponent.Output): Unit = when (output) {
        CreateDrinkComponent.Output.NavigateBack -> back()
        CreateDrinkComponent.Output.OpenImageActionsDialog -> modalNavigation.activate(
            ModalConfiguration.ImageActionsDialog
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