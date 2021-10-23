package com.utmaximur.alcoholtracker.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.dao.AlcoholTrackDao
import com.utmaximur.alcoholtracker.data.mapper.TrackMapper
import com.utmaximur.alcoholtracker.domain.entity.Track

class TrackRepository(
    alcoholTrackDatabase: AlcoholTrackDatabase,
    private val trackMapper: TrackMapper
) {

    private var trackDao: AlcoholTrackDao = alcoholTrackDatabase.getTrackDao()

    suspend fun getTrack(date: Long): Track {
        return trackMapper.map(trackDao.getTrack(date))
    }

    suspend fun singleRequestTracks(): List<Track> {
        return trackDao.singleRequestTracks().map { trackDBO ->
            TrackMapper().map(trackDBO)
        }
    }

    fun getTracks(): LiveData<List<Track>> {
        return Transformations.map(trackDao.getTracks()) { list ->
            list.map { trackDBO -> TrackMapper().map(trackDBO) }
        }
    }

    suspend fun insertTrack(track: Track) {
        trackDao.insertTrack(trackMapper.map(track))
    }

    suspend fun deleteTrack(track: Track) {
        trackDao.deleteTrack(trackMapper.map(track))
    }

    suspend fun updateTrack(track: Track) {
        trackDao.updateTrack(trackMapper.map(track))
    }
}