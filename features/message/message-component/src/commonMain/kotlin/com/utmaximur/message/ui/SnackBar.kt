package com.utmaximur.message.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import com.utmaximur.message.store.MessageStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
        type: SnackbarType,
        onSnackbarResult: (SnackbarResult) -> Unit = {},
    )
}

@Composable
fun ProvideSnackbarController(
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
) = LocalSnackbarController provides SnackbarController(
    snackbarHostState,
    coroutineScope,
)

@Composable
internal fun SnackbarMessageHandler(
    snackbarMessage: MessageStore.Label.SnackbarMessage,
    onDismiss: () -> Unit,
    snackbarController: SnackbarController = LocalSnackbarController.current,
) {
    val userMessage = snackbarMessage.userMessage
    val actionLabel = snackbarMessage.actionLabelMessage
    snackbarController.showMessage(
        message = userMessage,
        actionLabel = actionLabel,
        withDismissAction = snackbarMessage.withDismissAction,
        duration = snackbarMessage.duration,
        type = snackbarMessage.type,
        onSnackbarResult = snackbarMessage.onSnackbarResult,
    )
    onDismiss()
}

@Stable
private fun SnackbarController(
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
): SnackbarController = SnackbarControllerImpl(
    snackbarHostState,
    coroutineScope,
)

@Immutable
private class SnackbarControllerImpl(
    private val snackbarHostState: SnackbarHostState,
    private val coroutineScope: CoroutineScope,
) : SnackbarController {
    override fun showMessage(
        message: String,
        actionLabel: String?,
        withDismissAction: Boolean,
        duration: SnackbarDuration,
        type: SnackbarType,
        onSnackbarResult: (SnackbarResult) -> Unit,
    ) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                SnackbarVisualsCustom(
                    message = message,
                    actionLabel = actionLabel,
                    withDismissAction = withDismissAction,
                    duration = duration,
                    type = type,
                )
            ).let(onSnackbarResult)
        }
    }
}
