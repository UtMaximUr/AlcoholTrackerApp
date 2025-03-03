package com.utmaximur.data.tracksModal

import com.utmaximur.data.tracks.NAMED_TRACK_UI_MAPPER
import com.utmaximur.data.tracks.TrackUiMapper
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
    @Named(NAMED_TRACK_UI_MAPPER)
    val trackUiMapper: TrackUiMapper
) : TracksModalRepository {

    override val currencyStream = currencySettingsManager.currencyStateStream

    override fun observeTracksByIds(trackIds: List<Long>) = trackDao.getTracksByIds(trackIds)
        .mapList(trackUiMapper::transform)
}