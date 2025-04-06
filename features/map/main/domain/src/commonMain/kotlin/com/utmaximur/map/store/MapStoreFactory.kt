package com.utmaximur.map.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.domain.map.MapRepository
import com.utmaximur.domain.map.PlaceMark
import com.utmaximur.map.analytic_events.OpenScreenEvent
import com.utmaximur.map.store.MapStore.State
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

internal sealed interface Message {
    data class UpdatePlaceMarks(val places: List<PlaceMark>) : Message
    data class UpdateTheme(val isDarkTheme: Boolean) : Message
}

@Factory
internal class MapStoreFactory(
    storeFactory: StoreFactory,
    repository: MapRepository,
    analyticsManager: AnalyticsManager
) : MapStore,
    Store<Unit, State, Unit> by storeFactory.create(
        name = MapStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = coroutineExecutorFactory<_, _, _, Message, _> {
            onAction<Unit> {
                launch { analyticsManager.trackEvent(OpenScreenEvent()) }
                repository.darkThemeStateStream
                    .onEach { isDarkTheme -> dispatch(Message.UpdateTheme(isDarkTheme)) }
                    .launchIn(this)
                repository.observePlace()
                    .onEach { places -> dispatch(Message.UpdatePlaceMarks(places)) }
                    .launchIn(this)
            }
        },
        reducer = { message ->
            when (message) {
                is Message.UpdatePlaceMarks -> copy(places = message.places)
                is Message.UpdateTheme -> copy(isDarkTheme = message.isDarkTheme)
            }
        }
    )