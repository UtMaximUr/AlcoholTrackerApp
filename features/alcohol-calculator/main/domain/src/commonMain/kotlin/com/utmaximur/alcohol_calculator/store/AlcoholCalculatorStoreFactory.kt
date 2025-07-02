package com.utmaximur.alcohol_calculator.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.utmaximur.alcohol_calculator.interactor.BACCalculate
import com.utmaximur.alcohol_calculator.store.AlcoholCalculatorStore.Intent
import com.utmaximur.alcohol_calculator.store.AlcoholCalculatorStore.Label
import com.utmaximur.alcohol_calculator.store.AlcoholCalculatorStore.State
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.settingsManager.UserSettingsManager
import org.koin.core.annotation.Factory


@Factory
internal class AlcoholCalculatorStoreFactory(
    storeFactory: StoreFactory,
    userSettingsManager: UserSettingsManager,
    interactor: BACCalculate,
    analyticsManager: AnalyticsManager
) : AlcoholCalculatorStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = AlcoholCalculatorStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = {
            AlcoholCalculatorExecutor(
                userSettingsManager = userSettingsManager,
                interactor = interactor,
                analyticsManager = analyticsManager,
            )
        },
        reducer = AlcoholCalculatorReducer,
    )