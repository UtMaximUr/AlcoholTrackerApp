package com.utmaximur.alcoholtracker.domain.interactor

import com.utmaximur.alcoholtracker.data.repository.AssetsRepository
import com.utmaximur.alcoholtracker.data.repository.DrinkRepository
import com.utmaximur.alcoholtracker.domain.entity.Drink
import com.utmaximur.alcoholtracker.domain.entity.Icon
import javax.inject.Inject

class AddNewDrinkInteractor @Inject constructor(
    private val drinkRepository: DrinkRepository,
    private val assetsRepository: AssetsRepository
) {

    suspend fun insertDrink(drink: Drink) {
        drinkRepository.insertDrink(drink)
    }

    suspend fun updateDrink(drink: Drink) {
        drinkRepository.updateDrink(drink)
    }

    fun getIcons(): List<Icon> {
        return assetsRepository.getIcons()
    }
}