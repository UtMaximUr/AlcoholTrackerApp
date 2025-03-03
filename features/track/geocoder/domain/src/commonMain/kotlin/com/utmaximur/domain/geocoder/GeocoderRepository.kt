package com.utmaximur.domain.geocoder

import com.utmaximur.domain.models.Place
import kotlinx.coroutines.flow.Flow

interface GeocoderRepository {

    fun searchStream(query: SearchQuery): Flow<List<Place>>

    fun getPlaceByTrackId(trackId: Long): Flow<Place>
}