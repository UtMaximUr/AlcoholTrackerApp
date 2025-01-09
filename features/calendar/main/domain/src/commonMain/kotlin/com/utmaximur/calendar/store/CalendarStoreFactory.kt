package com.utmaximur.calendar.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.calendar.store.CalendarStore.Intent
import com.utmaximur.calendar.store.CalendarStore.Label
import com.utmaximur.calendar.store.CalendarStore.State
import com.utmaximur.domain.calendar.CalendarRepository
import org.koin.core.annotation.Factory

@Factory
internal class CalendarStoreFactory(
    storeFactory: StoreFactory,
    repository: CalendarRepository,
    analyticsManager: AnalyticsManager
) : CalendarStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = CalendarStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = {
            CalendarExecutor(
                repository = repository,
                analyticsManager = analyticsManager
            )
        },
        reducer = CalendarReducer
    )