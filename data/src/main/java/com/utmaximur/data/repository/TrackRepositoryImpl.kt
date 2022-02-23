package com.utmaximur.data.repository

import com.utmaximur.data.local_data_source.AlcoholTrackDatabase
import com.utmaximur.data.local_data_source.dao.AlcoholTrackDao
import com.utmaximur.data.local_data_source.mapper.TrackMapper
import com.utmaximur.domain.entity.Track
import com.utmaximur.domain.repository.TrackRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TrackRepositoryImpl @Inject constructor(
    alcoholTrackDatabase: AlcoholTrackDatabase,
    private val trackMapper: TrackMapper
) : TrackRepository {

    private var trackDao: AlcoholTrackDao = alcoholTrackDatabase.getTrackDao()

    override suspend fun getTracks(): List<Track> {
        return trackDao.getTracks().map { trackDBO ->
            TrackMapper().map(trackDBO)
        }
    }

    override suspend fun getTrackById(id: String): Track {
        return trackMapper.map(trackDao.getTrackById(id))
    }

    override suspend fun insertTrack(track: Track) {
        trackDao.insertTrack(trackMapper.map(track))
    }

    override suspend fun deleteTrack(track: Track) {
        trackDao.deleteTrack(trackMapper.map(track))
    }

    override suspend fun updateTrack(track: Track) {
        trackDao.updateTrack(trackMapper.map(track))
    }
}