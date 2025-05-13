package com.utmaximur.detailTrack.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.detailTrack.store.DetailTrackStore.Intent
import com.utmaximur.detailTrack.store.DetailTrackStore.Label
import com.utmaximur.detailTrack.store.DetailTrackStore.State
import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.Track
import com.utmaximur.domain.TrackData
import com.utmaximur.domain.ZERO_VALUE_F

interface DetailTrackStore : Store<Intent, State, Label> {

    data class State(
        val track: Track,
        val selectedDate: String,
        val price: Float,
        val currency: String
    ) {
        constructor() : this(
            track = Track.EMPTY,
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

        data class DateConfirmed(val date: Long?) : Label

        data class DateSelected(val date: String) : Label

        data object TrackLinked : Label

        data object CloseEvent : Label
    }
}