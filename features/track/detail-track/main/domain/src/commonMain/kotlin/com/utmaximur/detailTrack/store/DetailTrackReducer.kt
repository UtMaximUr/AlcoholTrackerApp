package com.utmaximur.detailTrack.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.utmaximur.core.mvi_mapper.RequestMapper
import com.utmaximur.detailTrack.store.DetailTrackStore.State
import com.utmaximur.mappers.implementation.RequestMappers

internal object DetailTrackReducer : Reducer<State, Message> {

    override fun State.reduce(msg: Message) = when (msg) {
        is Message.UpdateState -> {
            val newRequestUi = RequestMapper.builder(msg.request)
                .mapData(RequestMappers.data.single())
                .mapLoading(RequestMappers.loading.default())
                .build()
            copy(requestTrackUi = newRequestUi)
        }
        is Message.UpdatePrice -> copy(price = msg.price)
        is Message.UpdateSelectedDate -> copy(selectedDate = msg.date)
        is Message.UpdateCurrency -> copy(currency = msg.currency)
    }
}