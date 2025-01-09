package com.utmaximur.calendar.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.utmaximur.calendar.store.CalendarStore.State
import com.utmaximur.core.mvi_mapper.RequestMapper
import com.utmaximur.mappers.implementation.RequestMappers

internal object CalendarReducer : Reducer<State, Message> {

    override fun State.reduce(msg: Message) = when (msg) {
        is Message.UpdateState -> {
            val newRequestUi = RequestMapper.builder(msg.request)
                .mapData(RequestMappers.data.emptyMapToNull())
                .mapLoading(RequestMappers.loading.default())
                .build()
            copy(requestTracksUi = newRequestUi)
        }
        is Message.UpdateCalendarView -> copy(calendarView = msg.calendarView)
        is Message.UpdateCurrency -> copy(currency = msg.currency)
    }
}