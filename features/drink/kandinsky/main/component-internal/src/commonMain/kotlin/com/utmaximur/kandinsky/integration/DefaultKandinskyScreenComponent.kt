package com.utmaximur.kandinsky.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.kandinsky.KandinskyScreenComponent
import com.utmaximur.kandinsky.GenerateImageData
import com.utmaximur.kandinsky.store.KandinskyScreenStore
import com.utmaximur.kandinsky.ui.KandinskyScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultSplashScreenComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val output: (KandinskyScreenComponent.Output) -> Unit,
) : KandinskyScreenComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: KandinskyScreenStore = instanceKeeper.getStore(::get)
    private val requestBuilder = GenerateImageData.Builder()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<KandinskyScreenStore.State> = store.stateFlow

    override fun navigateBack() =
        store.accept(KandinskyScreenStore.Intent.Close)

    override fun generateImage() =
        store.accept(KandinskyScreenStore.Intent.Generate(requestBuilder.build()))

    override fun retryStylesLoading() =
        store.accept(KandinskyScreenStore.Intent.RetryStyles)

    override fun applyGeneratedImage() =
        store.accept(KandinskyScreenStore.Intent.GenerationCompletion)

    init {
        store.labels.onEach { event ->
            when (event) {
                KandinskyScreenStore.Label.CloseEvent ->
                    output(KandinskyScreenComponent.Output.NavigateBack)
            }
        }.launchIn(coroutineScope())
    }

    @Composable
    override fun Render(modifier: Modifier) = KandinskyScreen(this, requestBuilder)

}