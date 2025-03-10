package com.utmaximur.mappers.implementation

import com.utmaximur.core.logging.Logger
import com.utmaximur.core.mvi_mapper.ErrorHandler
import com.utmaximur.message.models.MessageContainer
import com.utmaximur.message.models.MessageService
import com.utmaximur.remote.errors.isNetworkConnectionError
import org.koin.core.annotation.Factory

@Factory
internal class RealNetworkHandler(
    private val logger: Logger,
    private val messageService: MessageService,
) : ErrorHandler {
    override fun handleError(e: Throwable) {
        logger.e { "[Network errors  -> ${e.stackTraceToString()}]" }
        when {
            e.isNetworkConnectionError() -> sendMessage("No internet connection")
            else -> sendMessage(e.toString())
        }
    }

    private fun sendMessage(message: String?) = messageService.showMessage(
        MessageContainer.SnackbarMessage(
            userMessage = message.orEmpty(),
            duration = MessageContainer.SnackbarMessage.SnackbarDuration.Long,
        ),
    )
}
