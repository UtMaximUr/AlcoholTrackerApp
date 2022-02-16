package com.utmaximur.alcoholtracker.presentation.dialog.delete

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.main.ui.theme.TextColorWhite

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeleteView(
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit
) {

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.dialog_delete_title),
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colors.onPrimary,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = { onNegativeClick() }) {
                        Text(
                            text = stringResource(id = R.string.dialog_no),
                            color = TextColorWhite
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    Button(onClick = { onPositiveClick() }) {
                        Text(
                            text = stringResource(id = R.string.dialog_ok),
                            color = TextColorWhite
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}