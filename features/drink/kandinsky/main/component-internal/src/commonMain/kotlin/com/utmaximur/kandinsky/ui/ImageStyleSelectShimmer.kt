package com.utmaximur.kandinsky.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.utmaximur.design.extensions.showShimmer
import com.utmaximur.design.shimmer.TextShimmer
import com.utmaximur.design.ui.ElevatedCardApp

@Composable
internal fun ImageStyleSelectShimmer() {
    ElevatedCardApp(
        contentPaddingValues = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextShimmer(aspectRatio = 6f)
        Box(
            modifier = Modifier
                .aspectRatio(16 / 6f)
                .clip(MaterialTheme.shapes.large)
                .showShimmer()
        )
        Box(
            modifier = Modifier
                .height(height = 18.dp)
                .aspectRatio(16f)
                .showShimmer()
                .align(Alignment.CenterHorizontally)
        )
    }
}