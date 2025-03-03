package com.utmaximur.geocoder.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.models.Place
import com.utmaximur.geocoder.store.GeocoderStore.Intent
import com.utmaximur.geocoder.store.GeocoderStore.Label
import com.utmaximur.geocoder.store.GeocoderStore.State

interface GeocoderStore : Store<Intent, State, Label> {

    data class State(
        val query: String,
        val places: List<Place>,
        val searchStarted: Boolean
    ) {
        constructor() : this(
            query = EMPTY_STRING,
            places = emptyList(),
            searchStarted = false
        )
    }

    sealed interface Intent {

        data class Search(val query: String) : Intent

        data class SelectedPlace(val place: Place) : Intent
    }

    sealed interface Label
}