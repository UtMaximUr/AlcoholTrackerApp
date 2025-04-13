package com.utmaximur.domain.createDrink

import com.utmaximur.domain.Drink
import kotlinx.coroutines.flow.Flow

interface CreateDrinkRepository {

    val iconsStream: Flow<List<Icon>>

    suspend fun saveDrink(drink: Drink)
}
