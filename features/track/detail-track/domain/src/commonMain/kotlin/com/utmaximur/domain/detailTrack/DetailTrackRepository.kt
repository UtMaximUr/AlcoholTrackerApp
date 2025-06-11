package com.utmaximur.domain.detailTrack

import com.utmaximur.domain.Track
import kotlinx.coroutines.flow.Flow

interface DetailTrackRepository {

    val currencyStream: Flow<String>

    suspend fun getTrackById(trackId: Long): Track

    suspend fun updateTrack(track: Track)

    suspend fun deleteTrack(id: Long)
}
