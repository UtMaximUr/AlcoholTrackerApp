package com.utmaximur.alcoholtracker.domain.interactor

import com.utmaximur.alcoholtracker.data.repository.TrackRepository
import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.domain.mapper.TrackListMapper
import javax.inject.Inject

class CalendarInteractor @Inject constructor(
    private val trackRepository: TrackRepository,
    private val trackListMapper: TrackListMapper
) {

    suspend fun getTracks(): List<Track> {
        return trackRepository.getTracks()
    }

    suspend fun getAlcoholTrackByDay(eventDay: Long): List<Track> {
        return trackListMapper.getAlcoholTrackByDay(trackRepository.getTracks(), eventDay)
    }
}