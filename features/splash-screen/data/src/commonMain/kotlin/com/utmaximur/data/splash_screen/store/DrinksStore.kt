package com.utmaximur.data.splash_screen.store

import com.utmaximur.app.base.coroutines.NamedCoroutineScopeIO
import com.utmaximur.data.splash_screen.mapper.DrinkRemoteMapper
import com.utmaximur.data.splash_screen.network.SplashScreenApi
import com.utmaximur.databaseRoom.drink.DbDrink
import com.utmaximur.databaseRoom.drink.DrinkDao
import kotlinx.coroutines.CoroutineScope
import org.koin.core.annotation.Factory
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.Validator

@Factory
internal class DrinksStore(
    splashScreenApi: SplashScreenApi,
    drinkDao: DrinkDao,
    @NamedCoroutineScopeIO
    ioScope: CoroutineScope,
    mapper: DrinkRemoteMapper
) : Store<Unit, List<DbDrink>> by StoreBuilder.from(
    fetcher = Fetcher.of {
        splashScreenApi.getDrinks()
    },
    sourceOfTruth = SourceOfTruth.Companion.of(
        reader = { drinkDao.getAll() },
        writer = { _: Unit, remoteData ->
            remoteData.forEach { drinkRemote ->
                drinkDao.insert(mapper.transform(drinkRemote))
            }
        }
    )
)
    .validator(Validator.by { it.isNotEmpty() })
    .scope(ioScope)
    .build()