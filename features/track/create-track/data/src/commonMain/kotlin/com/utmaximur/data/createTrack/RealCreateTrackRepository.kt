package com.utmaximur.data.createTrack

import com.utmaximur.domain.createTrack.CreateTrackRepository
import com.utmaximur.domain.models.Track
import com.utmaximur.settingsManager.CurrencySettingsManager
import com.utmaximur.utils.extensions.mapList
import org.koin.core.annotation.Factory
import com.utmaximur.databaseRoom.drink.DrinkDao
import com.utmaximur.databaseRoom.place.PlaceDao
import com.utmaximur.databaseRoom.track.TrackDao
import com.utmaximur.domain.models.Place

@Factory
internal class RealCreateTrackRepository(
    currencySettingsManager: CurrencySettingsManager,
    private val trackDao: TrackDao,
    private val drinkDao: DrinkDao,
    private val placeDao: PlaceDao,
    private val mapperHolder: MapperHolder
) : CreateTrackRepository {

    override val currencyStream = currencySettingsManager.currencyStateStream

    override val drinksStream = drinkDao.getAll()
        .mapList(mapperHolder.drinkUiMapper::transform)

    override suspend fun saveTrack(track: Track) =
        trackDao.insert(mapperHolder.trackLocalMapper.transform(track))

    override suspend fun savePlace(place: Place) {
        placeDao.insert(mapperHolder.placeLocalMapper.transform(place))
    }

    override suspend fun deleteDrink(id: Long) {
        drinkDao.deleteDrinkById(id)
    }
}