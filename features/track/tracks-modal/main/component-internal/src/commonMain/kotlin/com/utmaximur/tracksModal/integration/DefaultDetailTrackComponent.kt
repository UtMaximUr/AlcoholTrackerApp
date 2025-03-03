package com.utmaximur.tracksModal.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.tracksModal.TracksModalComponent
import com.utmaximur.tracksModal.store.TracksModalStore
import com.utmaximur.tracksModal.ui.TracksModalDialog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

@Factory
internal class DefaultTracksModalComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val trackIds: List<Long>,
    @InjectedParam private val output: (TracksModalComponent.Output) -> Unit
) : TracksModalComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: TracksModalStore = instanceKeeper.getStore {
        get { parametersOf(trackIds) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<TracksModalStore.State> = store.stateFlow

    override fun navigateToDetailScreen(trackId: Long) = output(
        TracksModalComponent.Output.NavigateToDetailTrackScreen(trackId)
    )

    override fun dismiss() = output(TracksModalComponent.Output.Dismiss)

    @Composable
    override fun Render(modifier: Modifier) = TracksModalDialog(this)

}