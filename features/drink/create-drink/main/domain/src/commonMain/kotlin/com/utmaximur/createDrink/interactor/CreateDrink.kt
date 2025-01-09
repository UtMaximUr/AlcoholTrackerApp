package com.utmaximur.createDrink.interactor

import com.utmaximur.app.base.coroutines.NamedCoroutineScopeIO
import com.utmaximur.createDrink.DrinkData
import com.utmaximur.domain.Interactor
import com.utmaximur.domain.createDrink.CreateDrinkRepository
import com.utmaximur.domain.models.Drink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
internal class CreateDrink(
    createDrinkRepository: Lazy<CreateDrinkRepository>,
    @NamedCoroutineScopeIO
    private val ioScope: CoroutineScope,
) : Interactor<DrinkData, Unit>() {

    private val repository by createDrinkRepository

    override fun doWork(params: DrinkData) {
        ioScope.launch { repository.saveDrink(params.toDrink()) }
    }

    private fun DrinkData.toDrink() = Drink(
        name = name,
        icon = icon.url,
        photo = photo
    )
}