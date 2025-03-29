package com.utmaximur.actions.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.utmaximur.design.ui.trailingOrBlockedIcon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ActionItem(
    icon: DrawableResource,
    title: StringResource,
    tinColor: Color = MaterialTheme.colorScheme.primary,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        headlineContent = {
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        leadingContent = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(icon),
                contentDescription = stringResource(title),
                tint = tinColor,
            )
        },
        trailingContent = trailingOrBlockedIcon(enabled = enabled),
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
        ),
    )
}
