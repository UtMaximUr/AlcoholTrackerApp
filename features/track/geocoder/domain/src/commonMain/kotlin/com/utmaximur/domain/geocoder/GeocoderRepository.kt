package com.utmaximur.domain.geocoder

import kotlinx.coroutines.flow.Flow

interface GeocoderRepository {

    val mapEnabledState: Flow<Boolean>

    fun searchStream(query: SearchQuery): Flow<List<Place>>

    fun getPlaceByTrackId(trackId: Long): Flow<Place>

    suspend fun savePlace(place: Place)
}
