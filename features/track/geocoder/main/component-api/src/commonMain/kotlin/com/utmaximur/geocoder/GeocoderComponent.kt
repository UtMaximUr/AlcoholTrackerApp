package com.utmaximur.geocoder

import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.domain.geocoder.Place
import com.utmaximur.geocoder.store.GeocoderStore
import kotlinx.coroutines.flow.StateFlow

interface GeocoderComponent : ComposeComponent {

    val model: StateFlow<GeocoderStore.State>

    fun onQueryChange(query: String)

    fun onPlaceSelected(place: Place)

    fun savePlaceToTrack(trackId: Long)
}
