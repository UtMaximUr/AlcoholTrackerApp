package com.utmaximur.splash.store.interactor

import com.utmaximur.connection.ObserveNetworkStatus
import com.utmaximur.connection.map
import com.utmaximur.domain.Interactor
import com.utmaximur.domain.splash_screen.SplashScreenRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class FetchData(
    private val networkStatus: ObserveNetworkStatus,
    splashScreenRepository: Lazy<SplashScreenRepository>,
) : Interactor<Unit, Flow<Boolean>>() {

    private val repository by splashScreenRepository

    override suspend fun doWork(params: Unit): Flow<Boolean> =
        networkStatus.invoke()
            .map(
                onAvailable = repository::fetchAppData,
                onUnavailable = repository::hasAllEssentialData,
            )
}
