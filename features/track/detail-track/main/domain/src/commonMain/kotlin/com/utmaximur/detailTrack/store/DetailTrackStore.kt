package com.utmaximur.detailTrack.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.detailTrack.store.DetailTrackStore.Intent
import com.utmaximur.detailTrack.store.DetailTrackStore.Label
import com.utmaximur.detailTrack.store.DetailTrackStore.State
import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.ZERO_VALUE_F
import com.utmaximur.domain.models.Track
import com.utmaximur.domain.models.TrackData

interface DetailTrackStore : Store<Intent, State, Label> {

    data class State(
        val requestTrackUi: RequestUi<Track>,
        val selectedDate: String,
        val price: Float,
        val currency: String
    ) {
        constructor() : this(
            requestTrackUi = RequestUi(),
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