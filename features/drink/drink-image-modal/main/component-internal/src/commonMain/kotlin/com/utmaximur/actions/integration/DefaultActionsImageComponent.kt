package com.utmaximur.actions.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.actions.ActionsImageComponent
import com.utmaximur.actions.store.ActionsImageStore
import com.utmaximur.actions.ui.ActionsSelectBottomSheet
import com.utmaximur.media.PlatformFile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultActionsImageComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val output: (ActionsImageComponent.Output) -> Unit,
) : ActionsImageComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: ActionsImageStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<ActionsImageStore.State> = store.stateFlow

    override fun addFile(platformFile: PlatformFile) =
        store.accept(ActionsImageStore.Intent.SelectedFile(platformFile))

    override fun addFiles(platformFiles: List<PlatformFile>) =
        store.accept(ActionsImageStore.Intent.SelectedFiles(platformFiles))

    override fun deleteFile() =
        store.accept(ActionsImageStore.Intent.DeleteFile)

    override fun navigateToKandinsky() =
        output(ActionsImageComponent.Output.KandinskyScreen)

    override fun dismiss() = output(ActionsImageComponent.Output.Dismiss)

    init {
        store.labels.onEach { event ->
            when (event) {
                is ActionsImageStore.Label.CloseEvent -> dismiss()
                ActionsImageStore.Label.DeleteFile -> dismiss()
            }
        }.launchIn(coroutineScope())
    }

    @Composable
    override fun Render(modifier: Modifier) = ActionsSelectBottomSheet(this)
}
