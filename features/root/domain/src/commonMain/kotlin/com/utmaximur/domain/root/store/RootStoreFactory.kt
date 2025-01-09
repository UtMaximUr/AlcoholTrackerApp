package com.utmaximur.domain.root.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.utmaximur.domain.root.store.RootStore.Intent
import com.utmaximur.domain.root.store.RootStore.Label
import com.utmaximur.domain.root.store.RootStore.State
import com.utmaximur.settingsManager.ThemeSettingsManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory

internal sealed interface Message {
    data class UpdateDarkTheme(val isDarkTheme: Boolean) : Message
    data class UpdateBottomBarState(val isVisible: Boolean) : Message
}

@Factory
internal class RootStoreFactory(
    storeFactory: StoreFactory,
    themeSettingsManager: ThemeSettingsManager
) : RootStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = RootStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = coroutineExecutorFactory<_, _, _, Message, _> {
            onAction<Unit> {
                themeSettingsManager.darkThemeStateStream.onEach { isDark ->
                    dispatch(Message.UpdateDarkTheme(isDarkTheme = isDark))
                }.launchIn(this)
            }
            onIntent<Intent> { intent ->
                when (intent) {
                    is Intent.HandleBottomBarState ->
                        dispatch(Message.UpdateBottomBarState(intent.isVisible))
                }
            }
        },
        reducer = { message ->
            when (message) {
                is Message.UpdateDarkTheme -> copy(isDarkTheme = message.isDarkTheme)
                is Message.UpdateBottomBarState -> copy(isBottomBarVisible = message.isVisible)
            }
        }
    )