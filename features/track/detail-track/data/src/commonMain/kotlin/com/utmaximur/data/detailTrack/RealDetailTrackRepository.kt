package com.utmaximur.data.detailTrack

import com.utmaximur.domain.detailTrack.DetailTrackRepository
import com.utmaximur.domain.models.Track
import com.utmaximur.settingsManager.CurrencySettingsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import com.utmaximur.databaseRoom.track.TrackDao

@Factory
internal class RealDetailTrackRepository(
    currencySettingsManager: CurrencySettingsManager,
    private val trackDao: TrackDao,
    private val mapper: MapperHolder
) : DetailTrackRepository {

    override val currencyStream = currencySettingsManager.currencyStateStream

    override fun observeTrackById(trackId: Long) = trackDao.getTrackById(trackId)
        .map(mapper.trackUiMapper::transform)

    override suspend fun updateTrack(track: Track) =
        trackDao.update(mapper.trackLocalMapper.transform(track))

    override suspend fun deleteTrack(id: Long) =
        withContext(Dispatchers.IO) { trackDao.deleteTrackById(id) }
}