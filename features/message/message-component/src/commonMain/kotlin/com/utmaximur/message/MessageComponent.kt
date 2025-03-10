package com.utmaximur.message

import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.utmaximur.message.store.MessageStore.Label
import kotlinx.coroutines.flow.Flow

interface MessageComponent : LifecycleOwner {

    val labels: Flow<Label>
}
