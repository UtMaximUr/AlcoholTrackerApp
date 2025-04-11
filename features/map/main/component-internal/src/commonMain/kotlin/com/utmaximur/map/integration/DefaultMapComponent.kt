package com.utmaximur.map.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.map.MapComponent
import com.utmaximur.map.store.MapStore
import com.utmaximur.map.ui.MapScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultMapComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val output: (MapComponent.Output) -> Unit,
) : MapComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: MapStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<MapStore.State> = store.stateFlow

    override fun onCreateTrackClick() =
        output(MapComponent.Output.NavigateCreateTrack)

    override fun onMapObjectsClick(ids: List<Long>) =
        output(MapComponent.Output.OpenTracksDialog(ids))

    override fun onMapObjectClick(id: Long) =
        output(MapComponent.Output.OpenTracksDialog(listOf(id)))

    @Composable
    override fun Render(modifier: Modifier) = MapScreen(modifier, this)
}
