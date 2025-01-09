package com.utmaximur.message.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.utmaximur.message.store.MessageStore

internal val LocalSnackbarController = staticCompositionLocalOf<SnackbarController> {
    error("You didn't providing a SnackbarController")
}

@Immutable
interface SnackbarController {

    fun showMessage(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = SnackbarDuration.Short,
        onSnackbarResult: (SnackbarResult) -> Unit = {}
    )
}

@Composable
fun ProvideSnackbarController(
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalSnackbarController provides SnackbarController(
            snackbarHostState,
            coroutineScope
        ),
        content = content
    )
}

@Composable
internal fun SnackbarMessageHandler(
    snackbarMessage: MessageStore.Label.SnackbarMessage,
    onDismiss: () -> Unit,
    snackbarController: SnackbarController = LocalSnackbarController.current
) {
    val userMessage = snackbarMessage.userMessage
    val actionLabel = snackbarMessage.actionLabelMessage
    LaunchedEffect(snackbarMessage) {
        snackbarController.showMessage(
            message = userMessage,
            actionLabel = actionLabel,
            withDismissAction = snackbarMessage.withDismissAction,
            duration = snackbarMessage.duration,
            onSnackbarResult = snackbarMessage.onSnackbarResult
        )
        onDismiss()
    }
}

@Stable
private fun SnackbarController(
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
): SnackbarController = SnackbarControllerImpl(
    snackbarHostState,
    coroutineScope
)

@Immutable
private class SnackbarControllerImpl(
    private val snackbarHostState: SnackbarHostState,
    private val coroutineScope: CoroutineScope
) : SnackbarController {
    override fun showMessage(
        message: String,
        actionLabel: String?,
        withDismissAction: Boolean,
        duration: SnackbarDuration,
        onSnackbarResult: (SnackbarResult) -> Unit
    ) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message,
                actionLabel,
                withDismissAction,
                duration
            ).let(onSnackbarResult)
        }
    }
}