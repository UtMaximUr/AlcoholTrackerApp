package com.utmaximur.createTrack.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.utmaximur.design.extensions.shimmer
import com.utmaximur.design.ui.ElevatedCardApp

@Composable
fun ItemDrinkShimmer() {
    ElevatedCardApp {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .shimmer(true)
                .fillMaxWidth()
                .aspectRatio(4 / 3f)
        )
    }
}