package com.utmaximur.domain.tracksModal

import com.utmaximur.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TracksModalRepository {

    val currencyStream: Flow<String>

    fun observeTracksByIds(trackIds: List<Long>): Flow<List<Track>>

}