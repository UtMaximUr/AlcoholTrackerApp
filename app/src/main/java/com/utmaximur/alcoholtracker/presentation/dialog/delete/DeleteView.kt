package com.utmaximur.alcoholtracker.presentation.dialog.delete

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.utmaximur.alcoholtracker.R

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
                    color = colorResource(id = R.color.text_color)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = { onNegativeClick() }) {
                        Text(
                            text = stringResource(id = R.string.dialog_select_date_no),
                            color = colorResource(id = R.color.text_color_white)
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    Button(onClick = { onPositiveClick() }) {
                        Text(
                            text = stringResource(id = R.string.dialog_select_date_ok),
                            color = colorResource(id = R.color.text_color_white)
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}