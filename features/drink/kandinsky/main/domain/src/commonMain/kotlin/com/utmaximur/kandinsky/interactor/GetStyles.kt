package com.utmaximur.kandinsky.interactor

import com.utmaximur.connection.NetworkStatus
import com.utmaximur.connection.ObserveNetworkStatus
import com.utmaximur.domain.Interactor
import com.utmaximur.domain.kandinsky.ImageStyle
import com.utmaximur.domain.kandinsky.KandinskyRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import org.koin.core.annotation.Factory

@Factory
internal class GetStyles(
    private val networkStatus: ObserveNetworkStatus,
    createDrinkRepository: Lazy<KandinskyRepository>
) : Interactor<Unit, Flow<List<ImageStyle>>>() {

    private val repository by createDrinkRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun doWork(params: Unit): Flow<List<ImageStyle>> {
        return networkStatus.invoke()
            .filterIsInstance<NetworkStatus.Available>()
            .flatMapLatest { repository.getStyles() }
    }
}