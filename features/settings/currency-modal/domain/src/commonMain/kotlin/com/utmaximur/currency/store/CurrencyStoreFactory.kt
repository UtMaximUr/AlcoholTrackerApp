package com.utmaximur.currency.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.currency.analytic_events.ChangeCurrencyEvent
import com.utmaximur.settingsManager.CurrencySettingsManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

internal sealed interface Message {
    data class UpdateCurrency(val currency: Currency) : Message
}

@Factory
internal class CurrencyStoreFactory(
    storeFactory: StoreFactory,
    currencySettingsManager: CurrencySettingsManager,
    analyticsManager: AnalyticsManager
) : CurrencyStore,
    Store<CurrencyStore.Intent, CurrencyStore.State, Nothing> by storeFactory.create(
        name = CurrencyStore::class.simpleName,
        initialState = CurrencyStore.State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = coroutineExecutorFactory<_, _, _, Message, _> {
            onAction<Unit> {
                launch {
                    currencySettingsManager.currencyStateStream.collectLatest { currency ->
                        dispatch(Message.UpdateCurrency(Currency.valueOf(currency)))
                    }
                }
            }
            onIntent<CurrencyStore.Intent> { intent ->
                when (intent) {
                    is CurrencyStore.Intent.SelectedCurrency -> launch {
                        analyticsManager.trackEvent(ChangeCurrencyEvent(intent.currency.name))
                        currencySettingsManager.saveCurrency(intent.currency.name)
                    }
                }
            }
        },
        reducer = { message ->
            when (message) {
                is Message.UpdateCurrency -> copy(currentCurrency = message.currency)
            }
        }
    )