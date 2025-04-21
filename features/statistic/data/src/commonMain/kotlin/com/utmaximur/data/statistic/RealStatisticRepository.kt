package com.utmaximur.data.statistic

import com.utmaximur.databaseRoom.drink.DrinkDao
import com.utmaximur.databaseRoom.track.TrackDao
import com.utmaximur.domain.statistic.StatisticRepository
import com.utmaximur.settingsManager.CurrencySettingsManager
import com.utmaximur.utils.extensions.mapList
import org.koin.core.annotation.Factory

@Factory
internal class RealStatisticRepository(
    trackDao: TrackDao,
    drinkDao: DrinkDao,
    mapperHolder: MapperHolder,
    currencySettingsManager: CurrencySettingsManager,
) : StatisticRepository {

    override val currencyStream = currencySettingsManager.currencyStateStream

    override val tracksStream = trackDao.getAll()
        .mapList(mapperHolder.trackDomainMapper::transform)

    override val drinksStream = drinkDao.getAll()
        .mapList(mapperHolder.drinkDomainMapper::transform)
}
