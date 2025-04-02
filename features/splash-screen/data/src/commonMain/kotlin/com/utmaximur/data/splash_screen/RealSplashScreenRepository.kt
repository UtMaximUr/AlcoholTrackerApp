package com.utmaximur.data.splash_screen

import com.utmaximur.data.splash_screen.store.DrinksStore
import com.utmaximur.data.splash_screen.store.IconsStore
import com.utmaximur.databaseRoom.drink.DrinkDao
import com.utmaximur.databaseRoom.icon.IconDao
import com.utmaximur.domain.splash_screen.SplashScreenRepository
import org.koin.core.annotation.Factory
import org.mobilenativefoundation.store.store5.impl.extensions.get

@Factory
internal class RealSplashScreenRepository(
    private val drinksStore: DrinksStore,
    private val iconsStore: IconsStore,
    private val iconDao: IconDao,
    private val drinkDao: DrinkDao
) : SplashScreenRepository {

    override suspend fun hasAllEssentialData() =
        iconDao.isTableNotEmpty() && drinkDao.isTableNotEmpty()

    override suspend fun fetchAppData(): Boolean {
        val drinks = drinksStore.get(Unit)
        val icons = iconsStore.get(Unit)
        return drinks.isNotEmpty() && icons.isNotEmpty()
    }
}