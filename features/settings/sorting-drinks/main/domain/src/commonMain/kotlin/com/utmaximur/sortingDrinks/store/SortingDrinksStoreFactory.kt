package com.utmaximur.sortingDrinks.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.sortingDrinks.store.SortingDrinksStore.Intent
import com.utmaximur.sortingDrinks.store.SortingDrinksStore.Label
import com.utmaximur.sortingDrinks.store.SortingDrinksStore.State
import com.utmaximur.domain.sortingDrinks.SortingDrinksRepository
import org.koin.core.annotation.Factory
import com.utmaximur.message.models.MessageService
import com.utmaximur.sortingDrinks.interactor.UpdateSortedDrinks

@Factory
internal class SortingDrinksStoreFactory(
    storeFactory: StoreFactory,
    repository: SortingDrinksRepository,
    interactor: UpdateSortedDrinks,
    messageService: MessageService,
    analyticsManager: AnalyticsManager
) : SortingDrinksStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = SortingDrinksStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = {
            SortingDrinksExecutor(
                repository = repository,
                interactor = interactor,
                messageService = messageService,
                analyticsManager = analyticsManager
            )
        },
        reducer = SortingDrinksReducer
    )