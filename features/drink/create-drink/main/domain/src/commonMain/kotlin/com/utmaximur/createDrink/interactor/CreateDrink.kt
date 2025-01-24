package com.utmaximur.createDrink.interactor

import com.utmaximur.createDrink.DrinkData
import com.utmaximur.domain.Interactor
import com.utmaximur.domain.createDrink.CreateDrinkRepository
import com.utmaximur.domain.models.Drink
import com.utmaximur.utils.extensions.localDateTimeNow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
internal class CreateDrink(
    createDrinkRepository: Lazy<CreateDrinkRepository>
) : Interactor<DrinkData, Unit>() {

    private val repository by createDrinkRepository

    override suspend fun doWork(params: DrinkData) {
        withContext(Dispatchers.IO) {
            repository.saveDrink(params.toDrink())
        }
    }

    private fun DrinkData.toDrink() = Drink(
        name = name,
        icon = icon.url,
        photo = photo,
        createdAt = localDateTimeNow
    )
}