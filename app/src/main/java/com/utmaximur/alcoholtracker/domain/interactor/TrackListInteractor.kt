package com.utmaximur.alcoholtracker.domain.interactor

import com.utmaximur.alcoholtracker.data.repository.TrackRepository
import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.domain.mapper.TrackListMapper
import javax.inject.Inject

class TrackListInteractor  @Inject constructor(
    private val trackRepository: TrackRepository,
    private val trackListMapper: TrackListMapper
) {

    suspend fun getAlcoholTrackByDay(eventDay: Long): List<Track> {
        return trackListMapper.getAlcoholTrackByDay(trackRepository.getTracks(), eventDay)
    }

    suspend fun deleteTrack(trackCalendar: Track) {
        trackRepository.deleteTrack(trackCalendar)
    }
}