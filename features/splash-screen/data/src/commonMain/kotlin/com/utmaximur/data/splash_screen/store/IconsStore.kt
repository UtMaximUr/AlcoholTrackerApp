package com.utmaximur.data.splash_screen.store

import com.utmaximur.app.base.coroutines.NamedCoroutineScopeIO
import com.utmaximur.data.splash_screen.mapper.IconRemoteMapper
import com.utmaximur.data.splash_screen.network.SplashScreenApi
import com.utmaximur.databaseRoom.icon.DbIcon
import com.utmaximur.databaseRoom.icon.IconDao
import kotlinx.coroutines.CoroutineScope
import org.koin.core.annotation.Factory
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.Validator

@Factory
internal class IconsStore(
    splashScreenApi: SplashScreenApi,
    iconDao: IconDao,
    @NamedCoroutineScopeIO
    ioScope: CoroutineScope,
    mapper: IconRemoteMapper
) : Store<Unit, List<DbIcon>> by StoreBuilder.from(
    fetcher = Fetcher.of {
        splashScreenApi.getIcons()
    },
    sourceOfTruth = SourceOfTruth.Companion.of(
        reader = { iconDao.getAll() },
        writer = { _: Unit, remoteData ->
            remoteData.forEach { iconRemote ->
                iconDao.insert(mapper.transform(iconRemote))
            }
        }
    )
)
    .validator(Validator.by { it.isNotEmpty() })
    .scope(ioScope)
    .build()