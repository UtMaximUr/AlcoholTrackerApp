package com.utmaximur.domain.interactor


import com.utmaximur.domain.entity.Drink
import com.utmaximur.domain.entity.Icon
import com.utmaximur.domain.repository.AssetsRepository
import com.utmaximur.domain.repository.DrinkRepository
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

    suspend fun getDrinkById(id: String): Drink {
        return drinkRepository.getDrinkById(id)
    }

    fun getIcons(): List<Icon> {
        return assetsRepository.getIcons()
    }
}