package com.utmaximur.geocoder.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.domain.geocoder.Place
import com.utmaximur.geocoder.store.GeocoderStore.Intent
import com.utmaximur.geocoder.store.GeocoderStore.Label
import com.utmaximur.geocoder.store.GeocoderStore.State

interface GeocoderStore : Store<Intent, State, Label> {

    data class State(
        val requestPlacesUi: RequestUi<List<Place>>,
        val selectedPlace: Place?,
        val searchStarted: Boolean,
        val isMapEnabled: Boolean,
    ) {
        val query: String = selectedPlace?.title.orEmpty()
        constructor() : this(
            requestPlacesUi = RequestUi(),
            selectedPlace = null,
            searchStarted = false,
            isMapEnabled = true,
        )
    }

    sealed interface Intent {

        data class SearchPlace(val query: String) : Intent

        data class SelectedPlace(val place: Place) : Intent

        data class SavePlace(val trackId: Long) : Intent
    }

    sealed interface Label
}
