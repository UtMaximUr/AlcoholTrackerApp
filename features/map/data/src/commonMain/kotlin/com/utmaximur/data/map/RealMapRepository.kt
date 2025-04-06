package com.utmaximur.data.map

import com.utmaximur.data.map.mappers.PlaceMarkUiMapper
import com.utmaximur.databaseRoom.place.PlaceDao
import com.utmaximur.domain.map.MapRepository
import com.utmaximur.settingsManager.ThemeSettingsManager
import com.utmaximur.utils.extensions.mapList
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class RealMapRepository(
    private val placeDao: PlaceDao,
    private val placeMarkUiMapper: PlaceMarkUiMapper,
    themeSettingsManager: ThemeSettingsManager,
) : MapRepository {

    override val darkThemeStateStream: Flow<Boolean> =
        themeSettingsManager.darkThemeStateStream

    override fun observePlace() = placeDao.getAll()
        .mapList(placeMarkUiMapper::transform)
}
