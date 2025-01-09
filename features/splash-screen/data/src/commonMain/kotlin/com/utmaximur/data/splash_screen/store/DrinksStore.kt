package com.utmaximur.data.splash_screen.store

import com.utmaximur.app.base.coroutines.NamedCoroutineScopeIO
import com.utmaximur.data.splash_screen.mapper.DrinkRemoteMapper
import com.utmaximur.data.splash_screen.network.SplashScreenApi
import kotlinx.coroutines.CoroutineScope
import org.koin.core.annotation.Factory
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import com.utmaximur.databaseRoom.drink.DrinkDao

@Factory
internal class DrinksStore(
    splashScreenApi: SplashScreenApi,
    drinkDao: DrinkDao,
    @NamedCoroutineScopeIO
    ioScope: CoroutineScope,
    mapper: DrinkRemoteMapper
) : Store<Unit, Unit> by StoreBuilder.from(
    fetcher = Fetcher.of {
        splashScreenApi.getDrinks()
    },
    sourceOfTruth = SourceOfTruth.Companion.of(
        nonFlowReader = {  },
        writer = { _: Unit, remoteData ->
            remoteData.forEach { drinkRemote ->
                drinkDao.insert(mapper.transform(drinkRemote))
            }
        }
    )
)
    .scope(ioScope)
    .build()