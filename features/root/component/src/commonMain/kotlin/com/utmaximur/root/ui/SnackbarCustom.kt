package com.utmaximur.root.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.utmaximur.message.ui.SnackbarType
import com.utmaximur.message.ui.SnackbarVisualsCustom
import com.utmaximur.root.ui.theme.ErrorContainerColorSnackbar
import com.utmaximur.root.ui.theme.InfoContainerColorSnackbar
import com.utmaximur.root.ui.theme.SuccessContainerColorSnackbar

@Composable
internal fun SnackbarCustom(
    visuals: SnackbarVisualsCustom,
    dismiss: () -> Unit,
) {
    Snackbar(
        modifier = Modifier.padding(8.dp),
        containerColor = getContainerColor(visuals.type),
        dismissAction = getDismissAction(visuals, dismiss),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = getContentIcon(visuals.type),
                contentDescription = visuals.type.name,
                tint = getContentColor(visuals.type),
            )
            Text(
                text = visuals.message,
                style = MaterialTheme.typography.bodyMedium,
                color = getContentColor(visuals.type),
            )
        }
    }
}

@Composable
private fun getDismissAction(
    visuals: SnackbarVisualsCustom,
    dismiss: () -> Unit
): (@Composable () -> Unit)? = if (visuals.withDismissAction) {
    @Composable {
        IconButton(
            onClick = dismiss,
            content = {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = Icons.Filled.Close.name,
                    tint = getContentColor(visuals.type)
                )
            }
        )
    }
} else null


@Composable
private fun getContentIcon(type: SnackbarType): ImageVector = when (type) {
    SnackbarType.ERROR -> Icons.Filled.Warning
    SnackbarType.INFO -> Icons.Filled.Info
    SnackbarType.SUCCESS -> Icons.Filled.Done
    SnackbarType.DEFAULT -> Icons.Filled.Email
}

@Composable
private fun getContentColor(type: SnackbarType): Color = when (type) {
    SnackbarType.DEFAULT -> MaterialTheme.colorScheme.primary
    SnackbarType.ERROR, SnackbarType.INFO, SnackbarType.SUCCESS -> Color.White
}

@Composable
private fun getContainerColor(type: SnackbarType): Color = when (type) {
    SnackbarType.ERROR -> ErrorContainerColorSnackbar
    SnackbarType.INFO -> InfoContainerColorSnackbar
    SnackbarType.SUCCESS -> SuccessContainerColorSnackbar
    SnackbarType.DEFAULT -> MaterialTheme.colorScheme.primaryContainer
}