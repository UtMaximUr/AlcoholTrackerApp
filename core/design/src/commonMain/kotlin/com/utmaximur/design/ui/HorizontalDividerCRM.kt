package com.utmaximur.design.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalDividerCRM(
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    horizontalPadding: Dp = 16.dp,
    color: Color = MaterialTheme.colorScheme.outline,
    thickness: Dp = DividerDefaults.Thickness
) {
    AnimatedVisibility(visible) {
        HorizontalDivider(
            modifier = modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = horizontalPadding),
            color = color,
            thickness = thickness
        )
    }
}