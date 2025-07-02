package com.utmaximur.alcohol_calculator.main_screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import features.alcohol_calculator.main.Res
import features.alcohol_calculator.main.cd_info
import features.alcohol_calculator.main.ic_info_24dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun InfoContent(text: String) = Row(
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Icon(
        modifier = Modifier.size(48.dp),
        painter = painterResource(Res.drawable.ic_info_24dp),
        tint = MaterialTheme.colorScheme.tertiary,
        contentDescription = stringResource(Res.string.cd_info),
    )
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
    )
}