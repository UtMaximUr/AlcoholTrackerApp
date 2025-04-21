package com.utmaximur.data.calendar

import com.utmaximur.data.tracks.NAMED_TRACK_DOMAIN_MAPPER
import com.utmaximur.data.tracks.TrackDomainMapper
import com.utmaximur.databaseRoom.track.TrackDao
import com.utmaximur.domain.calendar.CalendarRepository
import com.utmaximur.settingsManager.CurrencySettingsManager
import com.utmaximur.utils.extensions.mapList
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
internal class RealCalendarRepository(
    private val trackDao: TrackDao,
    @Named(NAMED_TRACK_DOMAIN_MAPPER)
    private val trackDomainMapper: TrackDomainMapper,
    currencySettingsManager: CurrencySettingsManager,
) : CalendarRepository {

    override val currencyStream = currencySettingsManager.currencyStateStream

    override fun observeTracksByMonth(startDate: Long, endDate: Long) = trackDao
        .getTrackByMonth(startDate, endDate)
        .mapList(trackDomainMapper::transform)
}
