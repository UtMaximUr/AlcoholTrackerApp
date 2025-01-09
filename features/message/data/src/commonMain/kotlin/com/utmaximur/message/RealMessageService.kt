package com.utmaximur.message

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.core.annotation.Single
import com.utmaximur.message.models.MessageContainer
import com.utmaximur.message.models.MessageService

@Single
internal class RealMessageService : MessageService {

    private val messageContainerChannel = Channel<MessageContainer>(Channel.UNLIMITED)

    override val messageContainerFlow = messageContainerChannel.receiveAsFlow()

    override fun showMessage(messageContainer: MessageContainer) {
        messageContainerChannel.trySend(messageContainer)
    }
}