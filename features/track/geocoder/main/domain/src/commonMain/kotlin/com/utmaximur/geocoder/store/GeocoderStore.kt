package com.utmaximur.geocoder.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.Place
import com.utmaximur.geocoder.store.GeocoderStore.Intent
import com.utmaximur.geocoder.store.GeocoderStore.Label
import com.utmaximur.geocoder.store.GeocoderStore.State

interface GeocoderStore : Store<Intent, State, Label> {

    data class State(
        val query: String,
        val requestPlacesUi: RequestUi<List<Place>>,
        val searchStarted: Boolean,
        val isMapEnabled: Boolean,
    ) {
        constructor() : this(
            query = EMPTY_STRING,
            requestPlacesUi = RequestUi(),
            searchStarted = false,
            isMapEnabled = true,
        )
    }

    sealed interface Intent {

        data class Search(val query: String) : Intent

        data class SelectedPlace(val place: Place) : Intent
    }

    sealed interface Label
}
