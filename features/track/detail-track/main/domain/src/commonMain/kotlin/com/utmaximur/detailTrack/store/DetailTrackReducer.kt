package com.utmaximur.detailTrack.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.utmaximur.detailTrack.store.DetailTrackStore.State

internal object DetailTrackReducer : Reducer<State, Message> {

    override fun State.reduce(msg: Message) = when (msg) {
        is Message.UpdateTrack -> copy(track = msg.track)
        is Message.UpdatePrice -> copy(price = msg.price)
        is Message.UpdateSelectedDate -> copy(selectedDate = msg.date)
        is Message.UpdateCurrency -> copy(currency = msg.currency)
    }
}