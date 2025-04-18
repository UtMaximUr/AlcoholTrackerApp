package com.utmaximur.domain.calendar

import com.utmaximur.domain.Track
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {

    val currencyStream: Flow<String>

    fun observeTracksByMonth(startDate: Long, endDate: Long): Flow<List<Track>>
}
