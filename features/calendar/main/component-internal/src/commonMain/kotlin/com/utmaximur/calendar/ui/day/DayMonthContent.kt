package com.utmaximur.calendar.ui.day

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.utmaximur.domain.models.Track

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun DayMonthContent(
    modifier: Modifier,
    tracks: List<Track>
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        tracks.forEach { track ->
            AsyncImage(
                modifier = Modifier
                    .height(32.dp)
                    .aspectRatio(1 / 2f),
                model = track.drink.icon,
                contentDescription = track.drink.name,
                contentScale = ContentScale.Crop
            )
        }
    }
}