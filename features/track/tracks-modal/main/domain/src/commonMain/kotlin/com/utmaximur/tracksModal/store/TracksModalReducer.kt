package com.utmaximur.tracksModal.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.utmaximur.tracksModal.store.TracksModalStore.State

internal object TracksModalReducer : Reducer<State, Message> {

    override fun State.reduce(msg: Message) = when (msg) {
        is Message.UpdateTracks -> copy(tracks = msg.tracks)
        is Message.UpdateCurrency -> copy(currency = msg.currency)
    }
}