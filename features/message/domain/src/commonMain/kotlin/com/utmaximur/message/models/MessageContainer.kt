package com.utmaximur.message.models

sealed interface MessageContainer {

    data class SimpleMessage(val text: String) : MessageContainer

    data class SnackbarMessage(
        val userMessage: String,
        val actionLabelMessage: String? = null,
        val withDismissAction: Boolean = false,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ) : MessageContainer {
        enum class SnackbarDuration {
            Short,
            Long,
            Indefinite
        }
    }
}