package com.utmaximur.splash.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.utmaximur.core.mvi_mapper.Request
import com.utmaximur.core.mvi_mapper.RequestMapper
import com.utmaximur.core.mvi_mapper.asRequest
import com.utmaximur.mappers.implementation.RequestMappers
import com.utmaximur.splash.store.SplashScreenStore.Intent
import com.utmaximur.splash.store.SplashScreenStore.Label
import com.utmaximur.splash.store.SplashScreenStore.State
import com.utmaximur.splash.store.interactor.FetchData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

internal sealed interface Message {
    data class UpdateState(val request: Request<Boolean>) : Message
}

@OptIn(ExperimentalCoroutinesApi::class)
@Factory
internal class SplashScreenFactory(
    storeFactory: StoreFactory,
    interactor: FetchData,
) : SplashScreenStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = SplashScreenStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = coroutineExecutorFactory<_, _, _, Message, _> {
            val readyToLoadFlow = MutableStateFlow(false)
            onAction<Unit> {
                launch {
                    interactor.doWork(Unit)
                        .asRequest()
                        .onEach { request -> dispatch(Message.UpdateState(request)) }
                        .flatMapLatest { readyToLoadFlow }
                        .filter { isReady -> isReady }
                        .map { Label.MainScreen }
                        .collect { label -> publish(label) }
                }
            }
            onIntent<Intent.ReadyToLoad> {
                readyToLoadFlow.update { !it }
            }
        },
        reducer = { message ->
            when (message) {
                is Message.UpdateState -> {
                    val newRequestUi = RequestMapper.builder(message.request)
                        .mapData(RequestMappers.data.single())
                        .mapLoading(RequestMappers.loading.simple())
                        .build()
                    copy(requestUi = newRequestUi)
                }
            }
        },
    )
