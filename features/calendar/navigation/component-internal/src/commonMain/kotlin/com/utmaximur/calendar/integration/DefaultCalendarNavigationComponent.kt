package com.utmaximur.calendar.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.utmaximur.calendar.CalendarComponent
import com.utmaximur.calendar.CalendarNavigationComponent
import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.createTrack.CreateTrackNavigationComponent
import com.utmaximur.detailTrack.DetailTrackNavigationComponent
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parameterArrayOf

@Factory
internal class DefaultCalendarNavigationComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam handleBottomBarState: (Boolean) -> Unit
) : CalendarNavigationComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val navigation = StackNavigation<Configuration>()

    private val stack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.CalendarScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    init {
        stack.subscribe { childStack ->
            val configuration = childStack.active.configuration
            val isVisibleBottomBar = when (configuration) {
                is Configuration.CreateTrackScreen,
                is Configuration.DetailTrackScreen -> false

                else -> true
            }
            handleBottomBarState(isVisibleBottomBar)
        }
    }

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): ComposeComponent =
        when (configuration) {
            is Configuration.CalendarScreen -> get<CalendarComponent> {
                parameterArrayOf(
                    componentContext,
                    ::onCalendarOutput
                )
            }

            is Configuration.CreateTrackScreen -> get<CreateTrackNavigationComponent> {
                parameterArrayOf(
                    componentContext,
                    { navigation.pop() }
                )
            }

            is Configuration.DetailTrackScreen -> get<DetailTrackNavigationComponent> {
                parameterArrayOf(
                    componentContext,
                    configuration.trackId,
                    { navigation.pop() }
                )
            }
        }

    private fun onCalendarOutput(output: CalendarComponent.Output): Unit = when (output) {
        CalendarComponent.Output.NavigateCreateTrack -> navigation.pushNew(
            Configuration.CreateTrackScreen
        )

        is CalendarComponent.Output.NavigateDetailTrack -> navigation.pushNew(
            Configuration.DetailTrackScreen(output.trackId)
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
    }
}