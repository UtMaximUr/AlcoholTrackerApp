package com.utmaximur.alcoholtracker.data.repository

import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.dao.AlcoholTrackDao
import com.utmaximur.alcoholtracker.data.mapper.TrackMapper
import com.utmaximur.alcoholtracker.domain.entity.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrackRepository(alcoholTrackDatabase: AlcoholTrackDatabase, private val trackMapper: TrackMapper) {

    private var trackDao: AlcoholTrackDao = alcoholTrackDatabase.getTrackDao()

    suspend fun getTrack(date: Long): Track {
        return trackMapper.map(trackDao.getTrack(date))
    }

    suspend fun getTracks(): List<Track> {
        return trackDao.getTracks().map { trackDBO ->
            TrackMapper().map(trackDBO)
        }
    }

    fun insertTrack(track: Track) {
        CoroutineScope(Dispatchers.IO).launch {
            trackDao.insertTrack(trackMapper.map(track))
        }
    }

    suspend fun deleteTrack(track: Track) {
        trackDao.deleteTrack(trackMapper.map(track))
    }

    fun updateTrack(track: Track) {
        CoroutineScope(Dispatchers.IO).launch {
            trackDao.updateTrack(trackMapper.map(track))
        }
    }
}