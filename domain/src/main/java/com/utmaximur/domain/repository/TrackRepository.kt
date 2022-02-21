package com.utmaximur.domain.repository

import com.utmaximur.domain.entity.Track

interface TrackRepository {
    suspend fun insertTrack(track: Track)
    suspend fun updateTrack(track: Track)
    suspend fun getTrackById(id: String): Track
    suspend fun getTracks(): List<Track>
    suspend fun deleteTrack(track: Track)
}