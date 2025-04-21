package com.utmaximur.data.sortingDrinks

import com.utmaximur.data.sortingDrinks.mapper.MapperHolder
import com.utmaximur.databaseRoom.drink.DrinkDao
import com.utmaximur.domain.sortingDrinks.SortingDrink
import com.utmaximur.domain.sortingDrinks.SortingDrinksRepository
import com.utmaximur.utils.extensions.mapList
import org.koin.core.annotation.Factory

@Factory
internal class RealSortingDrinksRepository(
    private val drinkDao: DrinkDao,
    private val mapperHolder: MapperHolder,
) : SortingDrinksRepository {

    override val sortingDrinksStream = drinkDao.getAll()
        .mapList(mapperHolder.sortingDrinkDomainMapper::transform)

    override suspend fun updateSortedDrinks(sortedDrinks: List<SortingDrink>) {
        drinkDao.updatePositions(sortedDrinks.map(mapperHolder.sortingDrinkLocalMapper::transform))
    }
}
