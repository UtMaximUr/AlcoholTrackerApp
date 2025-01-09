package com.utmaximur.createTrack.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.utmaximur.core.mvi_mapper.RequestMapper
import com.utmaximur.createTrack.store.CreateTrackStore.*
import com.utmaximur.mappers.implementation.RequestMappers

internal object CreateTrackReducer : Reducer<State, Message> {

    override fun State.reduce(msg: Message) = when (msg) {
        is Message.UpdateState -> {
            val newRequestUi = RequestMapper.builder(msg.request)
                .mapData(RequestMappers.data.emptyListToNull())
                .mapLoading(RequestMappers.loading.default())
                .build()
            copy(requestDrinksUi = newRequestUi)
        }
        is Message.UpdatePrice -> copy(price = msg.price)
        is Message.UpdateSelectedDate -> copy(selectedDate = msg.date)
        is Message.UpdateTotalPrice -> copy(totalPrice = msg.totalPrice)
        is Message.UpdateCurrency -> copy(currency = msg.currency)
    }
}