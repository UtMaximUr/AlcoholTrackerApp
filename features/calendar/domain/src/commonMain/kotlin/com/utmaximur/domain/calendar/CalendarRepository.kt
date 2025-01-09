package com.utmaximur.domain.calendar

import com.utmaximur.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {

    val currencyStream: Flow<String>

    fun observeTracksByStartDate(): Flow<List<Track>>
}