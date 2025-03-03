package com.utmaximur.geocoder

import com.utmaximur.geocoder.store.GeocoderStore
import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.domain.models.Place
import kotlinx.coroutines.flow.StateFlow

interface GeocoderComponent : ComposeComponent {

    val model: StateFlow<GeocoderStore.State>

    fun handleQuery(query: String)

    fun handleSelectedPlace(place: Place)
}