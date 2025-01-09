package com.utmaximur.message.integration

import androidx.compose.material3.SnackbarDuration
import com.utmaximur.message.models.MessageContainer
import com.utmaximur.message.store.MessageStore

internal fun MessageContainer.SnackbarMessage.toSnackbarMessageUi() = MessageStore.Label.SnackbarMessage(
    userMessage = userMessage,
    actionLabelMessage = actionLabelMessage,
    duration = duration.toUiDuration(),
    withDismissAction = withDismissAction
)
internal fun MessageContainer.SimpleMessage.toSnackbarMessageUi() = MessageStore.Label.SnackbarMessage(
    userMessage = text
)
private fun MessageContainer.SnackbarMessage.SnackbarDuration.toUiDuration() =
    when (this) {
        MessageContainer.SnackbarMessage.SnackbarDuration.Short -> SnackbarDuration.Short
        MessageContainer.SnackbarMessage.SnackbarDuration.Long -> SnackbarDuration.Long
        MessageContainer.SnackbarMessage.SnackbarDuration.Indefinite -> SnackbarDuration.Indefinite
    }