package com.utmaximur.kandinsky.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.utmaximur.core.mvi_mapper.ErrorHandler
import com.utmaximur.core.mvi_mapper.RequestMapper
import com.utmaximur.mappers.implementation.RequestMappers

internal class KandinskyReducer(
    private val errorHandler: ErrorHandler,
) : Reducer<KandinskyScreenStore.State, Message> {
    override fun KandinskyScreenStore.State.reduce(msg: Message) = when (msg) {
        is Message.UpdateNetworkStatus -> copy(internetAvailable = msg.available)
        is Message.UpdateGenerationResult -> copy(generationResult = msg.generationResult)
        is Message.UpdateStyles -> {
            val newRequestUi = RequestMapper.builder(msg.requestStylesUi)
                .mapData(RequestMappers.data.emptyListToNull())
                .mapLoading(RequestMappers.loading.simple())
                .handleError(RequestMappers.error.forced(errorHandler))
                .build()
            copy(requestStylesUi = newRequestUi)
        }
    }
}