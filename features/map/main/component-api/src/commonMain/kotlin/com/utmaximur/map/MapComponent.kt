package com.utmaximur.map

import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.map.store.MapStore
import kotlinx.coroutines.flow.StateFlow

interface MapComponent : ComposeComponent {

    val model: StateFlow<MapStore.State>

    fun onCreateTrackClick()

    fun onMapObjectsClick(ids: List<Long>)

    fun onMapObjectClick(id: Long)

    sealed interface Output {

        data object NavigateCreateTrack : Output

        data class OpenTracksDialog(val trackIds: List<Long>) : Output
    }
}
