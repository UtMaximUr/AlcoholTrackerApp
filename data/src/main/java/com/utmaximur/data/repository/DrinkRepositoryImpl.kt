package com.utmaximur.data.repository


import com.utmaximur.domain.entity.Drink
import com.utmaximur.data.local_data_source.AlcoholTrackDatabase
import com.utmaximur.data.local_data_source.dao.DrinkDao
import com.utmaximur.data.local_data_source.mapper.DrinkMapper
import com.utmaximur.domain.repository.DrinkRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DrinkRepositoryImpl @Inject constructor(
    alcoholTrackDatabase: AlcoholTrackDatabase,
    private val drinkMapper: DrinkMapper
) : DrinkRepository {

    private var drinkDao: DrinkDao = alcoholTrackDatabase.getDrinkDao()

    override suspend fun getDrinks(): List<Drink> {
        return drinkDao.getDrinks().map { drinkMapper.map(it) }
    }

    override suspend fun getDrinkById(id: String): Drink {
        return drinkMapper.map(drinkDao.getDrinkById(id))
    }

    override suspend fun insertDrink(drink: Drink) {
        drinkDao.addDrink(drinkMapper.map(drink))
    }

    override suspend fun updateDrink(drink: Drink) {
        drinkDao.updateDrink(drinkMapper.map(drink))
    }

    override suspend fun deleteDrink(drink: Drink) {
        drinkDao.deleteDrink(drinkMapper.map(drink))
    }
}