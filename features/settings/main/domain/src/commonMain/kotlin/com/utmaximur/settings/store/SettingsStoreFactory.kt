package com.utmaximur.settings.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.domain.settings.SettingsRepository
import com.utmaximur.settings.analytic_events.OpenScreenEvent
import org.koin.core.annotation.Factory
import com.utmaximur.settings.store.SettingsStore.Intent
import com.utmaximur.settings.store.SettingsStore.State
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

internal sealed interface Message {
    data class UpdateState(
        val appVersion: String,
        val isDarkTheme: Boolean,
        val currency: String
    ) : Message
}

@Factory
internal class SettingsStoreFactory(
    storeFactory: StoreFactory,
    settingsRepository: SettingsRepository,
    analyticsManager: AnalyticsManager
) : SettingsStore,
    Store<Intent, State, Nothing> by storeFactory.create(
        name = SettingsStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = coroutineExecutorFactory<_, _, _, Message, _> {
            onAction<Unit> {
                launch { analyticsManager.trackEvent(OpenScreenEvent()) }
                combine(
                    settingsRepository.appVersionStream,
                    settingsRepository.darkThemeStateStream,
                    settingsRepository.currencyStateStream
                ) { appVersion, isDarkTheme, currency ->
                    dispatch(
                        Message.UpdateState(
                            appVersion = appVersion,
                            isDarkTheme = isDarkTheme,
                            currency = currency
                        )
                    )
                }.launchIn(this)
            }
            onIntent<Intent> { intent ->
                when (intent) {
                    is Intent.ActiveDarkTheme -> launch {
                        settingsRepository.activeDarkTheme(intent.active)
                    }

                    is Intent.SelectedCurrency -> launch {
                        settingsRepository.saveCurrency(intent.currency)
                    }
                }
            }
        },
        reducer = { message ->
            when (message) {
                is Message.UpdateState -> copy(
                    appVersion = message.appVersion,
                    isDarkTheme = message.isDarkTheme,
                    currency = message.currency
                )
            }
        }
    )