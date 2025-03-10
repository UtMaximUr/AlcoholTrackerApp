package com.utmaximur.data.createDrink

import com.utmaximur.data.createDrink.mapper.MapperHolder
import com.utmaximur.databaseRoom.drink.DrinkDao
import com.utmaximur.databaseRoom.icon.IconDao
import com.utmaximur.domain.createDrink.CreateDrinkRepository
import com.utmaximur.domain.models.Drink
import com.utmaximur.utils.extensions.mapList
import org.koin.core.annotation.Factory

@Factory
internal class RealCreateDrinkRepository(
    iconDao: IconDao,
    private val drinkDao: DrinkDao,
    private val mapper: MapperHolder,
) : CreateDrinkRepository {

    override val iconsStream = iconDao.getAll()
        .mapList(mapper.iconLocalMapper::transform)

    override suspend fun saveDrink(drink: Drink) {
        drinkDao.insert(mapper.drinkLocalMapper.transform(drink))
    }
}
