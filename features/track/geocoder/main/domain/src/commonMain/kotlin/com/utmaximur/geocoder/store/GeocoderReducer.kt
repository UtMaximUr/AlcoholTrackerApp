package com.utmaximur.geocoder.store

import com.arkivanov.mvikotlin.core.store.Reducer

internal object GeocoderReducer : Reducer<GeocoderStore.State, Message> {
    override fun GeocoderStore.State.reduce(msg: Message) = when (msg) {
        is Message.UpdatePlaces -> copy(places = msg.places, searchStarted = false)
        is Message.UpdateQuery -> copy(query = msg.query)
        is Message.UpdateSearchStarted -> copy(searchStarted = msg.searchStarted)
    }
}