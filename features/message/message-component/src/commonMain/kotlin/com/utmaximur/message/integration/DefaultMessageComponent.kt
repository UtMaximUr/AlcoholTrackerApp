package com.utmaximur.message.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import com.utmaximur.message.MessageComponent
import com.utmaximur.message.models.MessageService
import com.utmaximur.message.store.MessageStore
import com.utmaximur.message.store.MessageStoreFactory
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultMessageComponent(
    @InjectedParam componentContext: ComponentContext
) : MessageComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: MessageStore = instanceKeeper.getStore(::get)

    override val labels: Flow<MessageStore.Label> = store.labels
}