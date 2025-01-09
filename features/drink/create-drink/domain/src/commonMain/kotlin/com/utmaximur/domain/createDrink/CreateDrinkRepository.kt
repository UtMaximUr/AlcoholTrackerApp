package com.utmaximur.domain.createDrink

import com.utmaximur.domain.models.Drink
import com.utmaximur.domain.models.Icon
import kotlinx.coroutines.flow.Flow

interface CreateDrinkRepository {

    val iconsStream: Flow<List<Icon>>

    suspend fun saveDrink(drink: Drink)
}