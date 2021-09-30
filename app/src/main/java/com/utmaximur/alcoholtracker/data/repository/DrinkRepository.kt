package com.utmaximur.alcoholtracker.data.repository


import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.dbo.DrinkDBO
import com.utmaximur.alcoholtracker.data.dao.DrinkDao


class DrinkRepository(alcoholTrackDatabase: AlcoholTrackDatabase) {

    private var drinkDao: DrinkDao = alcoholTrackDatabase.getDrinkDao()

    suspend fun getDrinks(): List<DrinkDBO> {
        return drinkDao.getDrinks()
    }

    suspend fun insertDrink(drinkDBO: DrinkDBO) {
        drinkDao.addDrink(drinkDBO)
    }

    suspend fun updateDrink(drinkDBO: DrinkDBO) {
        drinkDao.updateDrink(drinkDBO)
    }

    suspend fun deleteDrink(drinkDBO: DrinkDBO) {
        drinkDao.deleteDrink(drinkDBO)
    }
}