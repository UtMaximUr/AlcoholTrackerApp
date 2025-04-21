package com.utmaximur.sortingDrinks.interactor

import com.utmaximur.domain.Interactor
import com.utmaximur.domain.sortingDrinks.SortingDrink
import com.utmaximur.domain.sortingDrinks.SortingDrinksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
internal class UpdateSortedDrinks(
    sortingDrinksRepository: Lazy<SortingDrinksRepository>
) : Interactor<List<SortingDrink>, Unit>() {

    private val repository by sortingDrinksRepository

    override suspend fun doWork(params: List<SortingDrink>) {
        withContext(Dispatchers.IO) {
            val updatedSortedDrinks = params.mapIndexed { index, sortingDrink ->
                sortingDrink.copy(position = index)
            }
            repository.updateSortedDrinks(updatedSortedDrinks)
        }
    }
}