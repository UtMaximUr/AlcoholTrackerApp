package com.utmaximur.feature_calendar.track_list.ui

import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun LoadItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 160.dp)
            .placeholder(
                visible = true,
                color = Color.Transparent,
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = Color.LightGray,
                    animationSpec = infiniteRepeatable(
                        animation = tween(500)
                    )
                )
            )
    )
}