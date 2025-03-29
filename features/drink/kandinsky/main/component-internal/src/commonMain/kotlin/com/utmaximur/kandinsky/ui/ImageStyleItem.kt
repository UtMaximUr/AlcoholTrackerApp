package com.utmaximur.kandinsky.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.design.ui.ImageLoaderContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ImageStyleItem(
    title: String,
    imageUrl: String
) {
    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        Box {
            ImageLoaderContent(
                modifier = Modifier
                    .aspectRatio(16 / 6f)
                    .clip(MaterialTheme.shapes.large),
                imageUrl = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop
            )
            ElevatedCardApp(
                modifier = Modifier.padding(12.dp),
                shape = MaterialTheme.shapes.small,
                defaultElevation = 2.dp,
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}
