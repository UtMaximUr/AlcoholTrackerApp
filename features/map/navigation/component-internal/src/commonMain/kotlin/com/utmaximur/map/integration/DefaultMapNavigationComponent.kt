package com.utmaximur.map.integration

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
import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.core.decompose.ComposeDialogComponent
import com.utmaximur.createTrack.CreateTrackNavigationComponent
import com.utmaximur.detailTrack.DetailTrackNavigationComponent
import com.utmaximur.map.MapComponent
import com.utmaximur.map.MapNavigationComponent
import com.utmaximur.tracksModal.TracksModalComponent
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parameterArrayOf
import org.koin.core.parameter.parametersOf

@Factory
internal class DefaultMapNavigationComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam handleBottomBarState: (Boolean) -> Unit,
) : MapNavigationComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val navigation = StackNavigation<Configuration>()

    private val stack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.MapScreen,
        handleBackButton = true,
        childFactory = ::createChild,
    )

    init {
        stack.subscribe { childStack ->
            val configuration = childStack.active.configuration
            val isVisibleBottomBar = when (configuration) {
                is Configuration.CreateTrackScreen,
                is Configuration.DetailTrackScreen,
                -> false

                else -> true
            }
            handleBottomBarState(isVisibleBottomBar)
        }
    }

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext,
    ): ComposeComponent =
        when (configuration) {
            is Configuration.MapScreen -> get<MapComponent> {
                parameterArrayOf(
                    componentContext,
                    ::onCalendarOutput,
                )
            }

            is Configuration.CreateTrackScreen -> get<CreateTrackNavigationComponent> {
                parameterArrayOf(
                    componentContext,
                    { navigation.pop() },
                )
            }

            is Configuration.DetailTrackScreen -> get<DetailTrackNavigationComponent> {
                parameterArrayOf(
                    componentContext,
                    configuration.trackId,
                    { navigation.pop() },
                )
            }
        }

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
        is ModalConfiguration.TracksDialog -> get<TracksModalComponent> {
            parametersOf(
                componentContext,
                modalConfig.trackIds,
                ::onTracksModalOutput,
            )
        }
    }

    private fun onCalendarOutput(output: MapComponent.Output): Unit = when (output) {
        MapComponent.Output.NavigateCreateTrack -> navigation.pushNew(
            Configuration.CreateTrackScreen,
        )

        is MapComponent.Output.OpenTracksDialog -> modalNavigation.activate(
            ModalConfiguration.TracksDialog(output.trackIds),
        )
    }

    private fun onTracksModalOutput(output: TracksModalComponent.Output): Unit = when (output) {
        TracksModalComponent.Output.Dismiss -> modalNavigation.dismiss()
        is TracksModalComponent.Output.NavigateToDetailTrackScreen -> modalNavigation.dismiss {
            navigation.pushNew(Configuration.DetailTrackScreen(output.trackId))
        }
    }

    @Composable
    override fun Render(modifier: Modifier) {
        Children(
            stack = stack,
            animation = stackAnimation(slide()),
        ) { child ->
            child.instance.Render(modifier)
        }
        val modals by modalStack.subscribeAsState()
        modals.child?.instance?.also { modal ->
            modal.Render(modifier)
        }
    }
}
