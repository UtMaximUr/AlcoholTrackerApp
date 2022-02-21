package com.utmaximur.domain.repository

import com.utmaximur.domain.entity.Drink

interface DrinkRepository {
    suspend fun getDrinks(): List<Drink>
    suspend fun deleteDrink(drink: Drink)
    suspend fun insertDrink(drink: Drink)
    suspend fun updateDrink(drink: Drink)
    suspend fun getDrinkById(id: String): Drink
}