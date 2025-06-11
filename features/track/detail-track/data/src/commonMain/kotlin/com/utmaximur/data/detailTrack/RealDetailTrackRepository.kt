package com.utmaximur.data.detailTrack

import com.utmaximur.databaseRoom.track.TrackDao
import com.utmaximur.domain.Track
import com.utmaximur.domain.detailTrack.DetailTrackRepository
import com.utmaximur.settingsManager.CurrencySettingsManager
import org.koin.core.annotation.Factory

@Factory
internal class RealDetailTrackRepository(
    currencySettingsManager: CurrencySettingsManager,
    private val trackDao: TrackDao,
    private val mapper: MapperHolder,
) : DetailTrackRepository {

    override val currencyStream = currencySettingsManager.currencyStateStream

    override suspend fun getTrackById(trackId: Long): Track =
        trackDao.getTrackById(trackId).let(mapper.trackDomainMapper::transform)

    override suspend fun updateTrack(track: Track) =
        trackDao.update(mapper.trackLocalMapper.transform(track))

    override suspend fun deleteTrack(id: Long) = trackDao.deleteTrackById(id)
}