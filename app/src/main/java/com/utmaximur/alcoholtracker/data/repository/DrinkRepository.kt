package com.utmaximur.alcoholtracker.data.repository


import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.dao.DrinkDao
import com.utmaximur.alcoholtracker.data.mapper.DrinkMapper
import com.utmaximur.alcoholtracker.domain.entity.Drink


class DrinkRepository(alcoholTrackDatabase: AlcoholTrackDatabase, private val drinkMapper: DrinkMapper) {

    private var drinkDao: DrinkDao = alcoholTrackDatabase.getDrinkDao()

    suspend fun getDrinks(): List<Drink> {
        return drinkDao.getDrinks().map { drinkMapper.map(it) }
    }

    suspend fun getDrinkById(id: String): Drink {
        return drinkMapper.map(drinkDao.getDrinkById(id))
    }

    suspend fun insertDrink(drink: Drink) {
        drinkDao.addDrink(drinkMapper.map(drink))
    }

    suspend fun updateDrink(drink: Drink) {
        drinkDao.updateDrink(drinkMapper.map(drink))
    }

    suspend fun deleteDrink(drink: Drink) {
        drinkDao.deleteDrink(drinkMapper.map(drink))
    }
}