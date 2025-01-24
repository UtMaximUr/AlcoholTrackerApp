package com.utmaximur.domain.createTrack

import com.utmaximur.domain.models.Drink
import com.utmaximur.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface CreateTrackRepository {

    val currencyStream: Flow<String>
    val drinksStream: Flow<List<Drink>>

    suspend fun saveTrack(track: Track)

    suspend fun deleteDrink(id: Long)
}