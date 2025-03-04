package com.utmaximur.geocoder.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.utmaximur.core.mvi_mapper.ErrorHandler
import com.utmaximur.domain.geocoder.GeocoderRepository
import com.utmaximur.geocoder.store.GeocoderStore.Intent
import com.utmaximur.geocoder.store.GeocoderStore.Label
import com.utmaximur.geocoder.store.GeocoderStore.State
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam


@Factory
internal class GeocoderStoreFactory(
    storeFactory: StoreFactory,
    @InjectedParam trackId: Long?,
    geocoderRepository: GeocoderRepository,
    errorHandler: ErrorHandler
) : GeocoderStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = GeocoderStore::class.simpleName,
        initialState = State(),
        bootstrapper = GeocoderBootstrapper(trackId),
        executorFactory = {
            GeocoderExecutor(
                geocoderRepository = geocoderRepository
            )
        },
        reducer = GeocoderReducer(errorHandler)
    )