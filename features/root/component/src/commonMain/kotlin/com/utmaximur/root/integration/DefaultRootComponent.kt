package com.utmaximur.root.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.alcohol_calculator.AlcoholCalculatorComponent
import com.utmaximur.calendar.CalendarNavigationComponent
import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.createTrack.CreateTrackNavigationComponent
import com.utmaximur.domain.root.store.RootStore
import com.utmaximur.map.MapNavigationComponent
import com.utmaximur.message.MessageComponent
import com.utmaximur.root.RootComponent
import com.utmaximur.settings.SettingsNavigationComponent
import com.utmaximur.splash.SplashScreenComponent
import com.utmaximur.statistic.StatisticComponent
import com.utmaximur.widget.WidgetDeepLink
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parameterArrayOf

@Factory
internal class DefaultRootComponent(
    @InjectedParam componentContext: ComponentContext
) : RootComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: RootStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<RootStore.State> = store.stateFlow

    override val messageComponent: MessageComponent =
        get { parameterArrayOf(childContext(MessageComponent::class.simpleName.orEmpty())) }

    private val navigation = StackNavigation<Configuration>()

    private val _stack =
        childStack(
            source = navigation,
            serializer = Configuration.serializer(),
            initialConfiguration = Configuration.SplashScreen,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override val stack: Value<ChildStack<*, ComposeComponent>> = _stack

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): ComposeComponent =
        when (configuration) {
            Configuration.SplashScreen -> get<SplashScreenComponent> {
                parameterArrayOf(
                    componentContext,
                    ::onSplashScreenOutput
                )
            }

            Configuration.CalendarScreen -> get<CalendarNavigationComponent> {
                parameterArrayOf(componentContext)
            }

            Configuration.MapScreen -> get<MapNavigationComponent> {
                parameterArrayOf(componentContext)
            }

            Configuration.AlcoholCalculatorScreen -> get<AlcoholCalculatorComponent> {
                parameterArrayOf(componentContext)
            }

            Configuration.StatisticScreen -> get<StatisticComponent> {
                parameterArrayOf(componentContext)
            }

            Configuration.SettingsScreen -> get<SettingsNavigationComponent> {
                parameterArrayOf(componentContext)
            }

            Configuration.CreateTrackScreen -> get<CreateTrackNavigationComponent> {
                parameterArrayOf(
                    componentContext,
                    { navigation.pop() }
                )
            }
        }

    private fun onSplashScreenOutput(output: SplashScreenComponent.Output): Unit = when (output) {
        SplashScreenComponent.Output.NavigateToMainScreen ->
            navigation.replaceAll(Configuration.CalendarScreen)
    }

    override fun onCalendarScreenClicked() =
        navigation.replaceAll(Configuration.CalendarScreen)

    override fun onMapScreenClicked() =
        navigation.replaceAll(Configuration.MapScreen)

    override fun onAlcoholCalculatorScreenClicked() =
        navigation.replaceAll(Configuration.AlcoholCalculatorScreen)

    override fun onStatisticScreenClicked() =
        navigation.replaceAll(Configuration.StatisticScreen)

    override fun onSettingsScreenClicked() =
        navigation.replaceAll(Configuration.SettingsScreen)

    override fun handleDeepLink(deepLink: String?) {
        when (deepLink) {
            WidgetDeepLink.ADD_TRACK.deepLink -> navigation.replaceAll(
                Configuration.CalendarScreen,
                Configuration.CreateTrackScreen,
            )

            WidgetDeepLink.OPEN_APP.deepLink -> navigation.replaceAll(
                Configuration.SplashScreen
            )
        }
    }
}