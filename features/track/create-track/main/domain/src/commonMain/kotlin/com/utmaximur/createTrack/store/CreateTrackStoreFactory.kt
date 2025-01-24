package com.utmaximur.createTrack.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.createTrack.interactor.CreateTrack
import com.utmaximur.createTrack.store.CreateTrackStore.Intent
import com.utmaximur.createTrack.store.CreateTrackStore.Label
import com.utmaximur.createTrack.store.CreateTrackStore.State
import com.utmaximur.domain.calculator.CalculatorProviderData
import com.utmaximur.domain.confirmDialog.ConfirmDialogProviderData
import com.utmaximur.domain.createTrack.CreateTrackRepository
import com.utmaximur.domain.datePicker.DateProviderData
import org.koin.core.annotation.Factory
import com.utmaximur.message.models.MessageService

@Factory
internal class CreateTrackStoreFactory(
    storeFactory: StoreFactory,
    repository: CreateTrackRepository,
    interactor: CreateTrack,
    calculatorProviderData: CalculatorProviderData,
    dateProviderData: DateProviderData,
    confirmDialogProviderData: ConfirmDialogProviderData,
    messageService: MessageService,
    analyticsManager: AnalyticsManager
) : CreateTrackStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = CreateTrackStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = {
            CreateTrackExecutor(
                repository = repository,
                interactor = interactor,
                calculatorProviderData = calculatorProviderData,
                confirmDialogProviderData = confirmDialogProviderData,
                dateProviderData = dateProviderData,
                messageService = messageService,
                analyticsManager = analyticsManager
            )
        },
        reducer = CreateTrackReducer
    )