package com.utmaximur.detailTrack.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.detailTrack.interactor.UpdateTrack
import com.utmaximur.detailTrack.store.DetailTrackStore.Intent
import com.utmaximur.detailTrack.store.DetailTrackStore.Label
import com.utmaximur.detailTrack.store.DetailTrackStore.State
import com.utmaximur.domain.calculator.CalculatorProviderData
import com.utmaximur.domain.confirmDialog.ConfirmDialogProviderData
import com.utmaximur.domain.datePicker.DateProviderData
import com.utmaximur.domain.detailTrack.DetailTrackRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class DetailTrackStoreFactory(
    storeFactory: StoreFactory,
    @InjectedParam trackId: Long,
    repository: DetailTrackRepository,
    interactor: UpdateTrack,
    calculatorProviderData: CalculatorProviderData,
    dateProviderData: DateProviderData,
    confirmDialogProviderData: ConfirmDialogProviderData,
    analyticsManager: AnalyticsManager
) : DetailTrackStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = DetailTrackStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = {
            DetailTrackExecutor(
                trackId = trackId,
                repository = repository,
                interactor = interactor,
                calculatorProviderData = calculatorProviderData,
                dateProviderData = dateProviderData,
                confirmDialogProviderData = confirmDialogProviderData,
                analyticsManager = analyticsManager
            )
        },
        reducer = DetailTrackReducer
    )