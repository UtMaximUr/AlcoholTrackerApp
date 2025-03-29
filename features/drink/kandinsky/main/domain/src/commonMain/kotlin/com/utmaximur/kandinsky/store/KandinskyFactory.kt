package com.utmaximur.kandinsky.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.connection.ObserveNetworkStatus
import com.utmaximur.core.mvi_mapper.ErrorHandler
import com.utmaximur.domain.actions.PathFileProviderData
import com.utmaximur.domain.kandinsky.KandinskyRepository
import com.utmaximur.kandinsky.interactor.GenerateImage
import com.utmaximur.kandinsky.interactor.GetStyles
import com.utmaximur.kandinsky.store.KandinskyScreenStore.Intent
import com.utmaximur.kandinsky.store.KandinskyScreenStore.Label
import com.utmaximur.kandinsky.store.KandinskyScreenStore.State
import com.utmaximur.kandinsky.validation.RequestValidator
import com.utmaximur.message.models.MessageService
import org.koin.core.annotation.Factory

@Factory
internal class KandinskyFactory(
    storeFactory: StoreFactory,
    repository: KandinskyRepository,
    errorHandler: ErrorHandler,
    requestValidator: RequestValidator,
    messageService: MessageService,
    stylesInteractor: GetStyles,
    generateImageInteractor: GenerateImage,
    networkStatus: ObserveNetworkStatus,
    providerData: PathFileProviderData,
    analyticsManager: AnalyticsManager
) : KandinskyScreenStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = KandinskyScreenStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = {
            KandinskyExecutor(
                repository = repository,
                requestValidator = requestValidator,
                messageService = messageService,
                stylesInteractor = stylesInteractor,
                generateImageInteractor = generateImageInteractor,
                networkStatus = networkStatus,
                providerData = providerData,
                analyticsManager = analyticsManager
            )
        },
        reducer = KandinskyReducer(errorHandler)
    )
