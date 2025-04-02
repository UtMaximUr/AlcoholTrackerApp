package com.utmaximur.confirmDialog.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.utmaximur.confirmDialog.ConfirmDialogComponent
import com.utmaximur.design.extensions.bounceClick
import com.utmaximur.design.modal.ModalBottomSheetApp
import confirmDialog.resources.Res
import confirmDialog.resources.cancel
import confirmDialog.resources.confirm_delete
import confirmDialog.resources.confirm_delete_text
import confirmDialog.resources.proceed
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ConfirmDialog(
    component: ConfirmDialogComponent,
) {
    ModalBottomSheetApp(
        onDismissRequest = component::dismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(Res.string.confirm_delete),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                modifier = Modifier.padding(vertical = 24.dp),
                text = stringResource(Res.string.confirm_delete_text),
                style = MaterialTheme.typography.labelMedium,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .bounceClick(),
                    onClick = component::dismiss,
                    shape = MaterialTheme.shapes.large,
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
                        containerColor = MaterialTheme.colorScheme.tertiary,
                    ),
                ) {
                    Text(
                        text = stringResource(Res.string.proceed),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                    )
                }
            }
        }
    }
}
