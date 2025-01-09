package com.utmaximur.message.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.utmaximur.message.MessageComponent
import com.utmaximur.message.store.MessageStore

@Composable
fun MessageUi(
    component: MessageComponent
) {
    CompositionLocalProvider(LocalLifecycleOwner provides component) {
        component.labels.collectSideEffectWithLifecycle { sideEffect ->
            when (sideEffect) {
                is MessageStore.Label.SnackbarMessage -> {
                    SnackbarMessageHandler(
                        snackbarMessage = sideEffect,
                        onDismiss = {}
                    )
                }
            }
        }
    }
}