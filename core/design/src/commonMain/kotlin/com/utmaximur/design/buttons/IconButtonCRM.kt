package com.utmaximur.design.buttons

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.utmaximur.design.extensions.bounceClick
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun IconButtonCRM(
    icon: DrawableResource,
    color: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = Color.Transparent,
    onClick: () -> Unit = {}
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
        IconButton(
            modifier = Modifier.bounceClick(),
            onClick = onClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = backgroundColor
            )
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = color
            )
        }
    }
}