package com.utmaximur.domain.detailTrack

import com.utmaximur.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface DetailTrackRepository {

    val currencyStream: Flow<String>

    fun observeTrackById(trackId: Long): Flow<Track>

    suspend fun updateTrack(track: Track)

    suspend fun deleteTrack(id: Long)
}