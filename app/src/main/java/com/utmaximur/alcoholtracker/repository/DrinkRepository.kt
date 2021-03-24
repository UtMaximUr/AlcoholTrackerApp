package com.utmaximur.alcoholtracker.repository

import androidx.lifecycle.LiveData
import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.dao.DrinkDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DrinkRepository(alcoholTrackDatabase: AlcoholTrackDatabase) {

    private var drinkDao: DrinkDao = alcoholTrackDatabase.getDrinkDao()

    fun getDrinks(): LiveData<MutableList<Drink>> {
        return drinkDao.getDrinks()
    }

    fun insertDrink(drink: Drink) {
        CoroutineScope(Dispatchers.IO).launch {
            drinkDao.addDrink(drink)
        }
    }

    fun updateDrink(drink: Drink) {
        CoroutineScope(Dispatchers.IO).launch {
            drinkDao.updateDrink(drink)
        }
    }

    fun deleteDrink(drink: Drink) {
        CoroutineScope(Dispatchers.IO).launch {
            drinkDao.deleteDrink(drink)
        }
    }
}