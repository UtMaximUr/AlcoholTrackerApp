package com.utmaximur.mappers.implementation

import com.utmaximur.core.logging.Logger
import org.koin.core.annotation.Factory
import com.utmaximur.core.mvi_mapper.ErrorHandler
import com.utmaximur.message.models.MessageContainer
import com.utmaximur.message.models.MessageService
import com.utmaximur.remote.errors.isNetworkConnectionError

@Factory
internal class RealNetworkHandler(
    private val logger: Logger,
    private val messageService: MessageService
) : ErrorHandler {
    override fun handleError(e: Throwable) {
        logger.e { "[Network errors  -> ${e.stackTraceToString()}]" }
        when {
            e.isNetworkConnectionError() -> messageService.showMessage(
                MessageContainer.SnackbarMessage(
                    userMessage = "TODO()",
                    duration = MessageContainer.SnackbarMessage.SnackbarDuration.Long
                )
            )
        }
    }
}