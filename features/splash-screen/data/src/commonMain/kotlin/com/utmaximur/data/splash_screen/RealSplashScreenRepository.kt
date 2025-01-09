package com.utmaximur.data.splash_screen

import com.utmaximur.data.splash_screen.store.DrinksStore
import com.utmaximur.data.splash_screen.store.IconsStore
import com.utmaximur.domain.splash_screen.SplashScreenRepository
import org.koin.core.annotation.Factory
import org.mobilenativefoundation.store.store5.impl.extensions.fresh

@Factory
internal class RealSplashScreenRepository(
    private val drinksStore: DrinksStore,
    private val iconsStore: IconsStore
) : SplashScreenRepository {

    override suspend fun fetchAppData() {
        drinksStore.fresh(Unit)
        iconsStore.fresh(Unit)
    }
}