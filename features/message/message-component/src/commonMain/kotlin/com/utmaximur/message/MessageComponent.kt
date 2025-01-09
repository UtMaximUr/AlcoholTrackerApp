package com.utmaximur.message

import com.arkivanov.essenty.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.Flow
import com.utmaximur.message.store.MessageStore.Label

interface MessageComponent : LifecycleOwner {

    val labels: Flow<Label>
}