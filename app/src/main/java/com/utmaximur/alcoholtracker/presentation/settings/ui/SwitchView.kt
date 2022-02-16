package com.utmaximur.alcoholtracker.presentation.settings.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.utmaximur.alcoholtracker.R

@Composable
fun Switch(
    text: Int,
    checkedState: Boolean,
    onClick: (Boolean) -> Unit
) {
    Row {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
            text = stringResource(id = text),
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = MaterialTheme.colors.onPrimary
        )
        androidx.compose.material.Switch(modifier = Modifier
            .padding(12.dp),
            checked = checkedState,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.primary,
                checkedTrackColor = MaterialTheme.colors.primary
            ),
            onCheckedChange = { onClick(it) })
    }
}