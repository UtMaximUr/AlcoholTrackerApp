package com.utmaximur.alcoholtracker.data.repository

import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.dao.AlcoholTrackDao
import com.utmaximur.alcoholtracker.data.dbo.TrackDBO
import com.utmaximur.alcoholtracker.data.mapper.TrackMapper
import com.utmaximur.alcoholtracker.domain.entity.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrackRepository(alcoholTrackDatabase: AlcoholTrackDatabase) {

    private var trackDao: AlcoholTrackDao = alcoholTrackDatabase.getTrackDao()

    suspend fun getTrack(date: Long): TrackDBO {
        return trackDao.getTrack(date)
    }

    suspend fun getTracks(): List<Track> {
        return trackDao.getTracks().map { trackDBO ->
            TrackMapper().map(trackDBO)
        }
    }

    fun insertTrack(trackDBO: TrackDBO) {
        CoroutineScope(Dispatchers.IO).launch {
            trackDao.insertTrack(trackDBO)
        }
    }

    suspend fun deleteTrack(trackDBO: TrackDBO) {
        trackDao.deleteTrack(trackDBO)
    }

    fun updateTrack(trackDBO: TrackDBO) {
        CoroutineScope(Dispatchers.IO).launch {
            trackDao.updateTrack(trackDBO)
        }
    }
}