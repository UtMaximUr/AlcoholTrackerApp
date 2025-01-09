package com.utmaximur.data.calendar

import com.utmaximur.data.tracks.NAMED_TRACK_UI_MAPPER
import com.utmaximur.data.tracks.TrackUiMapper
import com.utmaximur.domain.calendar.CalendarRepository
import com.utmaximur.settingsManager.CurrencySettingsManager
import com.utmaximur.utils.extensions.mapList
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import com.utmaximur.databaseRoom.track.TrackDao

@Factory
internal class RealCalendarRepository(
    private val trackDao: TrackDao,
    @Named(NAMED_TRACK_UI_MAPPER)
    private val trackUiMapper: TrackUiMapper,
    currencySettingsManager: CurrencySettingsManager
) : CalendarRepository {

    override val currencyStream = currencySettingsManager.currencyStateStream

    override fun observeTracksByStartDate() = trackDao
        .getTracksByStartDate()
        .mapList(trackUiMapper::transform)

}