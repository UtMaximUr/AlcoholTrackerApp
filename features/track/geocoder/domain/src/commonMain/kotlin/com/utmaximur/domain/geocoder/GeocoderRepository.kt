package com.utmaximur.domain.geocoder

import com.utmaximur.domain.Place
import kotlinx.coroutines.flow.Flow

interface GeocoderRepository {

    val mapEnabledState: Flow<Boolean>

    fun searchStream(query: SearchQuery): Flow<List<Place>>

    fun getPlaceByTrackId(trackId: Long): Flow<Place>
}
