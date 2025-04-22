package com.utmaximur.domain.root.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.utmaximur.app.base.app.ApplicationInfo
import com.utmaximur.domain.root.store.RootStore.Intent
import com.utmaximur.domain.root.store.RootStore.Label
import com.utmaximur.domain.root.store.RootStore.State
import com.utmaximur.settingsManager.ThemeSettingsManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory

internal sealed interface Message {
    data class UpdateDarkTheme(val isDarkTheme: Boolean) : Message
    data class UpdateMapState(val isMapEnabled: Boolean) : Message
}

@Factory
internal class RootStoreFactory(
    storeFactory: StoreFactory,
    themeSettingsManager: ThemeSettingsManager,
    applicationInfo: ApplicationInfo,
) : RootStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = RootStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = coroutineExecutorFactory<_, _, _, Message, _> {
            onAction<Unit> {
                val isMapEnabled = applicationInfo.flavor.isMapAvailable()
                dispatch(Message.UpdateMapState(isMapEnabled = isMapEnabled))
                themeSettingsManager.darkThemeStateStream.onEach { isDark ->
                    dispatch(Message.UpdateDarkTheme(isDarkTheme = isDark))
                }.launchIn(this)
            }
        },
        reducer = { message ->
            when (message) {
                is Message.UpdateDarkTheme -> copy(isDarkTheme = message.isDarkTheme)
                is Message.UpdateMapState -> copy(isMapEnabled = message.isMapEnabled)
            }
        },
    )
