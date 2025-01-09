package com.utmaximur.design.shimmer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.design.extensions.shimmer

@Composable
fun TextShimmer(aspectRatio: Float) {
    Box(
        modifier = Modifier
            .shimmer(true)
            .height(height = 18.dp)
            .aspectRatio(aspectRatio)
    )
}