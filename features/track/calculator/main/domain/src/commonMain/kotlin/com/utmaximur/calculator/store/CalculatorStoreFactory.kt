package com.utmaximur.calculator.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.github.murzagalin.evaluator.Evaluator
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.calculator.CalculatorItemBuilder
import com.utmaximur.calculator.store.CalculatorStore.Intent
import com.utmaximur.calculator.store.CalculatorStore.Label
import com.utmaximur.calculator.store.CalculatorStore.State
import com.utmaximur.domain.calculator.CalculatorProviderData
import org.koin.core.annotation.Factory


@Factory
internal class CalculatorStoreFactory(
    storeFactory: StoreFactory,
    evaluator: Evaluator,
    providerData: CalculatorProviderData,
    calculatorItemBuilder: CalculatorItemBuilder,
    analyticsManager: AnalyticsManager
) : CalculatorStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = CalculatorStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = {
            CalculatorExecutor(
                evaluator = evaluator,
                providerData = providerData,
                itemBuilder = calculatorItemBuilder,
                analyticsManager = analyticsManager
            )
        },
        reducer = CalculatorReducer
    )