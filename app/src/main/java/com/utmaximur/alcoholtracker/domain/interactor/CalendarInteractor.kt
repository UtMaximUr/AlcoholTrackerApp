package com.utmaximur.alcoholtracker.domain.interactor

import com.utmaximur.alcoholtracker.data.repository.TrackRepository
import com.utmaximur.alcoholtracker.domain.entity.TrackCalendar
import com.utmaximur.alcoholtracker.domain.mapper.CalendarMapper
import javax.inject.Inject

class CalendarInteractor @Inject constructor(
    private val trackRepository: TrackRepository,
    private val calendarMapper: CalendarMapper
) {

    suspend fun getTrack(date: Long): TrackCalendar {
        return calendarMapper.map(trackRepository.getTrack(date))
    }

    suspend fun getTracks(): List<TrackCalendar> {
        return calendarMapper.mapList(trackRepository.getTracks())
    }

    suspend fun deleteTrack(trackCalendar: TrackCalendar) {
        trackRepository.deleteTrack(calendarMapper.map(trackCalendar))
    }

    suspend fun getAlcoholTrackByDay(eventDay: Long): List<TrackCalendar> {
        return calendarMapper.getAlcoholTrackByDay(trackRepository.getTracks(), eventDay)
    }
}