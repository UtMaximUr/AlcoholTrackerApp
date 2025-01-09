package com.utmaximur.createTrack.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.createTrack.store.CreateTrackStore.Intent
import com.utmaximur.createTrack.store.CreateTrackStore.Label
import com.utmaximur.createTrack.store.CreateTrackStore.State
import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.ZERO_VALUE_F
import com.utmaximur.domain.models.Drink
import com.utmaximur.domain.models.TrackData

interface CreateTrackStore : Store<Intent, State, Label> {

    data class State(
        val requestDrinksUi: RequestUi<List<Drink>>,
        val totalPrice: String,
        val selectedDate: String,
        val price: Float,
        val currency: String
    ) {
        constructor() : this(
            requestDrinksUi = RequestUi(),
            totalPrice = EMPTY_STRING,
            selectedDate = EMPTY_STRING,
            price = ZERO_VALUE_F,
            currency = EMPTY_STRING
        )
    }

    sealed interface Intent {

        data class SelectedDate(val date: String) : Intent

        data object Today : Intent

        data class SaveTrackData(val trackData: TrackData) : Intent

    }

    sealed interface Label {

        data class DatePickerEvent(val date: Long?) : Label

        data object CloseEvent : Label
    }
}