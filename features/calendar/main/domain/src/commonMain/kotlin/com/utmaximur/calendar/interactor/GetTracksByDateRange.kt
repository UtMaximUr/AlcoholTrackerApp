package com.utmaximur.calendar.interactor

import com.utmaximur.calendar.store.DateRange
import com.utmaximur.calendar.store.TracksData
import com.utmaximur.domain.Interactor
import com.utmaximur.domain.Track
import com.utmaximur.domain.calendar.CalendarRepository
import com.utmaximur.utils.extensions.toLocalDate
import com.utmaximur.utils.extensions.toLong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
internal class GetTracksByDateRange(
    calendarRepository: Lazy<CalendarRepository>
) : Interactor<DateRange, Flow<TracksData>>() {

    private val repository by calendarRepository

    override suspend fun doWork(params: DateRange): Flow<TracksData> {
        val startDate = params.first.toLong()
        val endDate = params.second.toLong()
        return repository.observeTracksByMonth(startDate, endDate)
            .filter { tracks -> tracks.isNotEmpty() }
            .mapToGroupedDates()
    }

    private fun Flow<List<Track>>.mapToGroupedDates() = this
        .map { tracks -> tracks.groupBy { it.date } }
        .map { grouped -> grouped.mapKeys { (date, _) -> date.toLocalDate() } }
}