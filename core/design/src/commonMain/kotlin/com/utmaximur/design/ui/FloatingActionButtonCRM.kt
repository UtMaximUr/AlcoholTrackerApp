package com.utmaximur.design.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun FloatingActionButtonCRM(
    modifier: Modifier = Modifier,
    image: DrawableResource,
    isVisible: Boolean = true,
    onActionClick: () -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible
    ) {
        FloatingActionButton(
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.tertiary,
            onClick = onActionClick
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(image),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}