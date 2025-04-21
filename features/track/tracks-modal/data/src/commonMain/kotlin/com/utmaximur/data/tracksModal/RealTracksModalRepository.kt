package com.utmaximur.data.tracksModal

import com.utmaximur.data.tracks.NAMED_TRACK_DOMAIN_MAPPER
import com.utmaximur.data.tracks.TrackDomainMapper
import com.utmaximur.databaseRoom.track.TrackDao
import com.utmaximur.domain.tracksModal.TracksModalRepository
import com.utmaximur.settingsManager.CurrencySettingsManager
import com.utmaximur.utils.extensions.mapList
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
internal class RealTracksModalRepository(
    currencySettingsManager: CurrencySettingsManager,
    private val trackDao: TrackDao,
    @Named(NAMED_TRACK_DOMAIN_MAPPER)
    val trackDomainMapper: TrackDomainMapper,
) : TracksModalRepository {

    override val currencyStream = currencySettingsManager.currencyStateStream

    override fun observeTracksByIds(trackIds: List<Long>) = trackDao.getTracksByIds(trackIds)
        .mapList(trackDomainMapper::transform)
}
