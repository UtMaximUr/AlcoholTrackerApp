package com.utmaximur.settings.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import settings.resources.Res
import settings.resources.ic_chevrone_right

@Composable
internal fun SettingsButton(
    modifier: Modifier = Modifier,
    title: StringResource,
    trailingContent: @Composable (() -> Unit)? = { DefaultTrailingItem() }
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.labelMedium
            )
        },
        trailingContent = trailingContent,
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
internal fun DefaultTrailingItem() = Icon(
    modifier = Modifier.size(16.dp),
    painter = painterResource(Res.drawable.ic_chevrone_right),
    contentDescription = null,
    tint = MaterialTheme.colorScheme.primary
)