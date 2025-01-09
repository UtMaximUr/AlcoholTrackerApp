package com.utmaximur.message.models

import kotlinx.coroutines.flow.Flow

interface MessageService {

    val messageContainerFlow: Flow<MessageContainer>

    fun showMessage(messageContainer: MessageContainer)
}