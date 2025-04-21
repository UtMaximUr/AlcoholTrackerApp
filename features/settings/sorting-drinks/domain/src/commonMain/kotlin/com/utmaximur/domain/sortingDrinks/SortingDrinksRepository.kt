package com.utmaximur.domain.sortingDrinks

import kotlinx.coroutines.flow.Flow

interface SortingDrinksRepository {

    val sortingDrinksStream: Flow<List<SortingDrink>>

    suspend fun updateSortedDrinks(sortedDrinks: List<SortingDrink>)

}
