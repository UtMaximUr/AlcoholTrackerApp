package com.utmaximur.domain.interactor


import com.utmaximur.domain.entity.EventDay
import com.utmaximur.domain.entity.Track
import com.utmaximur.domain.mapper.TrackListMapper
import com.utmaximur.domain.repository.TrackRepository
import javax.inject.Inject

class CalendarInteractor @Inject constructor(
    private val trackRepository: TrackRepository,
    private val trackListMapper: TrackListMapper
) {

    suspend fun getTracks(): List<EventDay> {
        return trackListMapper.getEventsDayList(trackRepository.getTracks())
    }

    suspend fun getAlcoholTrackByDay(eventDay: Long): List<Track> {
        return trackListMapper.getAlcoholTrackByDay(trackRepository.getTracks(), eventDay)
    }
}