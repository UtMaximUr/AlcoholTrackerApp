package com.utmaximur.data.detailTrack

import com.utmaximur.databaseRoom.place.PlaceDao
import com.utmaximur.databaseRoom.track.TrackDao
import com.utmaximur.domain.detailTrack.DetailTrackRepository
import com.utmaximur.domain.Place
import com.utmaximur.domain.Track
import com.utmaximur.settingsManager.CurrencySettingsManager
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
internal class RealDetailTrackRepository(
    currencySettingsManager: CurrencySettingsManager,
    private val trackDao: TrackDao,
    private val placeDao: PlaceDao,
    private val mapper: MapperHolder,
) : DetailTrackRepository {

    override val currencyStream = currencySettingsManager.currencyStateStream

    override fun observeTrackById(trackId: Long) = trackDao.getTrackById(trackId)
        .map(mapper.trackDomainMapper::transform)

    override suspend fun updateTrack(track: Track) =
        trackDao.update(mapper.trackLocalMapper.transform(track))

    override suspend fun deleteTrack(id: Long) = trackDao.deleteTrackById(id)

    override suspend fun updatePlace(place: Place) =
        placeDao.upsert(mapper.placeLocalMapper.transform(place))
}