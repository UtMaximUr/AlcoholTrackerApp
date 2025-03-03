package com.utmaximur.tracksModal

import com.utmaximur.core.decompose.ComposeDialogComponent
import com.utmaximur.tracksModal.store.TracksModalStore
import kotlinx.coroutines.flow.StateFlow

interface TracksModalComponent : ComposeDialogComponent {

    val model: StateFlow<TracksModalStore.State>

    fun navigateToDetailScreen(trackId: Long)

    sealed interface Output {

        data class NavigateToDetailTrackScreen(val trackId: Long) : Output

        data object Dismiss : Output

    }
}