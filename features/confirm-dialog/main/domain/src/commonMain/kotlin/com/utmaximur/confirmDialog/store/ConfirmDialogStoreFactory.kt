package com.utmaximur.confirmDialog.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.confirmDialog.analytic_events.OpenScreenEvent
import com.utmaximur.confirmDialog.store.ConfirmDialogStore.Intent
import com.utmaximur.confirmDialog.store.ConfirmDialogStore.Label
import com.utmaximur.confirmDialog.store.ConfirmDialogStore.State
import com.utmaximur.domain.confirmDialog.ConfirmDialogProviderData
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory


@Factory
internal class ConfirmDialogStoreFactory(
    storeFactory: StoreFactory,
    providerData: ConfirmDialogProviderData,
    analyticsManager: AnalyticsManager
) : ConfirmDialogStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = ConfirmDialogStore::class.simpleName,
        initialState = State,
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = coroutineExecutorFactory {
            onAction<Unit> {
                launch { analyticsManager.trackEvent(OpenScreenEvent()) }
            }
            onIntent<Intent> { intent ->
                when (intent) {
                    is Intent.Confirm -> launch {
                        providerData.sendData(intent.id)
                        publish(Label.CloseEvent)
                    }
                }
            }
        }
    )