package com.utmaximur.createDrink.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.createDrink.interactor.CreateDrink
import com.utmaximur.createDrink.store.CreateDrinkStore.Intent
import com.utmaximur.createDrink.store.CreateDrinkStore.Label
import com.utmaximur.createDrink.store.CreateDrinkStore.State
import com.utmaximur.createDrink.validation.DrinkValidator
import com.utmaximur.domain.actions.PlatformFileProviderData
import com.utmaximur.domain.createDrink.CreateDrinkRepository
import com.utmaximur.message.models.MessageService
import org.koin.core.annotation.Factory

@Factory
internal class CreateDrinkStoreFactory(
    storeFactory: StoreFactory,
    analyticsManager: AnalyticsManager,
    providerData: PlatformFileProviderData,
    messageService: MessageService,
    drinkValidator: DrinkValidator,
    createDrinkRepository: CreateDrinkRepository,
    interactor: CreateDrink
) : CreateDrinkStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = CreateDrinkStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = {
            CreateDrinkExecutor(
                analyticsManager = analyticsManager,
                providerData = providerData,
                messageService = messageService,
                drinkValidator = drinkValidator,
                createDrinkRepository = createDrinkRepository,
                interactor = interactor
            )
        },
        reducer = CreateDrinkReducer
    )