package com.utmaximur.splash.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.utmaximur.domain.splash_screen.SplashScreenRepository
import com.utmaximur.splash.store.SplashScreenStore.Intent
import com.utmaximur.splash.store.SplashScreenStore.Label
import com.utmaximur.splash.store.SplashScreenStore.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
internal class SplashScreenFactory(
    storeFactory: StoreFactory,
    splashScreenRepository: SplashScreenRepository
) : SplashScreenStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = SplashScreenStore::class.simpleName,
        initialState = State,
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = coroutineExecutorFactory {
            val readyToLoadFlow = MutableStateFlow(false)
            onAction<Unit> {
                launch {
                    splashScreenRepository.fetchAppData()
                    readyToLoadFlow
                        .filter { isReady -> isReady }
                        .map { Label.MainScreen }
                        .collect { label -> publish(label) }
                }
            }
            onIntent<Intent> { intent ->
                when (intent) {
                    is Intent.ReadyToLoad -> readyToLoadFlow.update { true }
                }
            }
        },
        reducer = { State }
    )
