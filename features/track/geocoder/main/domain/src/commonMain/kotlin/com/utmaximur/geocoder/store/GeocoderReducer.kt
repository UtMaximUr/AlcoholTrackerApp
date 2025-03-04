package com.utmaximur.geocoder.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.utmaximur.core.mvi_mapper.RequestMapper
import com.utmaximur.mappers.implementation.RequestMappers

internal object GeocoderReducer : Reducer<GeocoderStore.State, Message> {
    override fun GeocoderStore.State.reduce(msg: Message) = when (msg) {
        is Message.UpdatePlaces -> {
            val newRequestUi = RequestMapper.builder(msg.requestPlacesUi)
                .mapData(RequestMappers.data.single())
                .mapLoading(RequestMappers.loading.default())
                .build()
            copy(requestPlacesUi = newRequestUi, searchStarted = false)
        }

        is Message.UpdateQuery -> copy(query = msg.query)
        is Message.UpdateSearchStarted -> copy(searchStarted = msg.searchStarted)
        is Message.UpdateMapState -> copy(isMapEnabled = msg.isMapEnabled)
    }
}