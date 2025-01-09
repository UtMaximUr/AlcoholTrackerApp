package com.utmaximur.design.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun IconToggleButtonCRM(
    icon: DrawableResource,
    iconChecked: DrawableResource,
    checked: Boolean,
    uncheckedColor: Color = MaterialTheme.colorScheme.primary,
    checkedColor: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 42.dp,
    iconPadding: Dp = 4.dp,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    IconToggleButton(
        modifier = Modifier.size(size),
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = IconButtonDefaults.iconToggleButtonColors(
            containerColor = Color.Transparent,
            checkedContainerColor = Color.Transparent
        )
    ) {
        Icon(
            painter = when {
                checked -> painterResource(iconChecked)
                else -> painterResource(icon)
            },
            tint = when {
                checked -> checkedColor
                else -> uncheckedColor
            },
            modifier = Modifier.size(size).padding(iconPadding),
            contentDescription = null
        )
    }
}