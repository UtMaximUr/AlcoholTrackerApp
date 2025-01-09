package com.utmaximur.message.store

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.message.store.MessageStore.Label
import com.utmaximur.message.store.MessageStore.State
import com.utmaximur.message.store.MessageStore.Intent

interface MessageStore : Store<Intent, State, Label> {
    data object State
    sealed interface Label {
        data class SnackbarMessage(
            val userMessage: String,
            val actionLabelMessage: String? = null,
            val withDismissAction: Boolean = false,
            val duration: SnackbarDuration = SnackbarDuration.Short,
            val onSnackbarResult: (SnackbarResult) -> Unit = {}
        ) : Label
    }
    sealed interface Intent
}