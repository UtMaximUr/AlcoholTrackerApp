package com.utmaximur.alcoholtracker.domain.interactor

import androidx.lifecycle.LiveData
import com.utmaximur.alcoholtracker.data.repository.TrackRepository
import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.domain.mapper.CalendarMapper
import javax.inject.Inject

class CalendarInteractor @Inject constructor(
    private val trackRepository: TrackRepository,
    private val calendarMapper: CalendarMapper
) {

    suspend fun getTrack(date: Long): Track {
        return trackRepository.getTrack(date)
    }

    fun getTracks(): LiveData<List<Track>> {
        return trackRepository.getTracks()
    }

    suspend fun deleteTrack(trackCalendar: Track) {
        trackRepository.deleteTrack(trackCalendar)
    }

    suspend fun getAlcoholTrackByDay(eventDay: Long): List<Track> {
        return calendarMapper.getAlcoholTrackByDay(trackRepository.singleRequestTracks(), eventDay)
    }
}