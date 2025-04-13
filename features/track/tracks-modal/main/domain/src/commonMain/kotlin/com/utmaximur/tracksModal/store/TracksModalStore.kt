package com.utmaximur.tracksModal.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.Track
import com.utmaximur.tracksModal.store.TracksModalStore.Intent
import com.utmaximur.tracksModal.store.TracksModalStore.Label
import com.utmaximur.tracksModal.store.TracksModalStore.State

interface TracksModalStore : Store<Intent, State, Label> {

    data class State(
        val tracks: List<Track>,
        val currency: String
    ) {
        constructor() : this(
            tracks = emptyList(),
            currency = EMPTY_STRING
        )
    }

    sealed interface Intent

    sealed interface Label
}