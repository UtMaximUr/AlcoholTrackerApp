package com.utmaximur.message.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.utmaximur.message.integration.toSnackbarMessageUi
import com.utmaximur.message.models.MessageContainer
import com.utmaximur.message.models.MessageService
import com.utmaximur.message.store.MessageStore.Intent
import com.utmaximur.message.store.MessageStore.Label
import com.utmaximur.message.store.MessageStore.State
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory

@Factory
internal class MessageStoreFactory(
    storeFactory: StoreFactory,
    messageService: MessageService
) : MessageStore,
    Store<Intent, State, Label> by storeFactory.create(
        name = MessageStore::class.simpleName,
        initialState = State,
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = coroutineExecutorFactory {
            onAction<Unit> {
                messageService.messageContainerFlow.onEach { messageContainer ->
                    when (messageContainer) {
                        is MessageContainer.SimpleMessage -> publish(messageContainer.toSnackbarMessageUi())
                        is MessageContainer.SnackbarMessage -> publish(messageContainer.toSnackbarMessageUi())
                    }
                }.launchIn(this)
            }
        }
    )