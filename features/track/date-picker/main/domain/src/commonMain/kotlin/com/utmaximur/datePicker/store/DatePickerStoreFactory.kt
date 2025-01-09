package com.utmaximur.datePicker.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.utmaximur.datePicker.store.DatePickerStore.Intent
import com.utmaximur.datePicker.store.DatePickerStore.Label
import com.utmaximur.datePicker.store.DatePickerStore.State
import com.utmaximur.domain.datePicker.DateProviderData
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

internal sealed interface Message {
    data class UpdateState(val selectedDate: Long?) : Message
}

@Factory
internal class DatePickerStoreFactory(
    storeFactory: StoreFactory,
    @InjectedParam selectedDate: Long?,
    providerData: DateProviderData,
) : DatePickerStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = DatePickerStore::class.simpleName,
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = coroutineExecutorFactory<_, _, _, Message, _> {
            onAction<Unit> {
                dispatch(Message.UpdateState(selectedDate = selectedDate))
            }
            onIntent<Intent> { intent ->
                when (intent) {
                    is Intent.SelectedDate -> launch {
                        providerData.sendData(intent.date)
                        publish(Label.CloseEvent)
                    }
                }
            }
        },
        reducer = { message ->
            when (message) {
                is Message.UpdateState -> copy(selectedDate = message.selectedDate)
            }
        }
    )