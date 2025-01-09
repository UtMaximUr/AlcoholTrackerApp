package com.utmaximur.confirmDialog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.utmaximur.confirmDialog.ConfirmDialogComponent
import com.utmaximur.design.extensions.bounceClick
import confirmDialog.resources.Res
import confirmDialog.resources.cancel
import confirmDialog.resources.cd_close
import confirmDialog.resources.confirm_delete
import confirmDialog.resources.confirm_delete_text
import confirmDialog.resources.ic_close_button
import confirmDialog.resources.proceed
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
internal fun ConfirmDialog(
    component: ConfirmDialogComponent
) {
    Dialog(
        onDismissRequest = component::dismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.extraLarge
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.confirm_delete),
                    style = MaterialTheme.typography.titleLarge
                )
                Icon(
                    painter = painterResource(Res.drawable.ic_close_button),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(size = 24.dp)
                        .clickable(onClick = component::dismiss),
                    contentDescription = stringResource(Res.string.cd_close),
                )
            }
            Text(
                text = stringResource(Res.string.confirm_delete_text),
                style = MaterialTheme.typography.labelMedium
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .bounceClick(),
                    onClick = component::dismiss,
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        text = stringResource(Res.string.cancel),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .bounceClick(),
                    onClick = component::confirm,
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(
                        text = stringResource(Res.string.proceed),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}