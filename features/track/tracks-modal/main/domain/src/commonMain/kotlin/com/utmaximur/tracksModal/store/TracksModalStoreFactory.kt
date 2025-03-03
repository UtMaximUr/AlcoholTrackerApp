package com.utmaximur.tracksModal.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.domain.tracksModal.TracksModalRepository
import com.utmaximur.tracksModal.store.TracksModalStore.Intent
import com.utmaximur.tracksModal.store.TracksModalStore.Label
import com.utmaximur.tracksModal.store.TracksModalStore.State
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class DetailTrackStoreFactory(
    storeFactory: StoreFactory,
    @InjectedParam trackIds: List<Long>,
    repository: TracksModalRepository,
    analyticsManager: AnalyticsManager
) : TracksModalStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = TracksModalStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = {
            TracksModalExecutor(
                trackIds = trackIds,
                repository = repository,
                analyticsManager = analyticsManager
            )
        },
        reducer = TracksModalReducer
    )