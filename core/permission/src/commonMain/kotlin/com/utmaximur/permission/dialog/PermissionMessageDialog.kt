package com.utmaximur.permission.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
internal fun PermissionMessageDialog(
    message: String,
    onPositiveClick: () -> Unit = {},
    onNegativeClick: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onNegativeClick,
        containerColor = MaterialTheme.colorScheme.onBackground,
        title = {
            Text(
                text = "Res.string.permission_title",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        },
        confirmButton = {
            TextButton(
                onClick = onPositiveClick
            ) {
                Text(
                    text = "Res.string.permission_settings",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onNegativeClick
            ) {
                Text(
                    text = "Res.string.permission_cancel",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 13.sp,
                )
            }
        }
    )
}