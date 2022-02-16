package com.utmaximur.alcoholtracker.presentation.dialog.update

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utmaximur.alcoholtracker.R

@Composable
fun UpdateDialog(
    onLaterClick: () -> Unit,
    onNowClick: () -> Unit
) {

    Column {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_update_black_24dp),
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = stringResource(id = R.string.update_title),
                color = MaterialTheme.colors.onPrimary
            )
        }
        Row(modifier = Modifier.padding(16.dp)) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { onNowClick() })
            {
                Text(text = stringResource(id = R.string.update_restart))
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Button(
                modifier = Modifier.weight(1f),
                onClick = { onLaterClick() })
            {
                Text(text = stringResource(id = R.string.update_later))
            }
        }
    }
}