package com.utmaximur.splash.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.utmaximur.splash.SplashScreenComponent
import com.utmaximur.splash.store.SplashScreenStore
import com.utmaximur.splash.ui.SplashScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultSplashScreenComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val output: (SplashScreenComponent.Output) -> Unit,
) : SplashScreenComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: SplashScreenStore = instanceKeeper.getStore(::get)

    override fun readyToLoad() = store.accept(SplashScreenStore.Intent.ReadyToLoad)

    init {
        store.labels.onEach { event ->
            when (event) {
                SplashScreenStore.Label.MainScreen ->
                    output(SplashScreenComponent.Output.NavigateToMainScreen)
            }
        }.launchIn(coroutineScope())
    }

    @Composable
    override fun Render(modifier: Modifier) = SplashScreen(this)

}