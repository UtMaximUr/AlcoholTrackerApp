package com.utmaximur.calendar.ui.day

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import calendar.resources.Res
import calendar.resources.calendar_empty
import com.utmaximur.domain.models.Track
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DayContent(
    tracks: List<Track>,
    currency: String,
    onItemClick: (Long) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tracks) { track ->
            TrackItem(
                track = track,
                currency = currency,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
internal fun DayEmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Res.string.calendar_empty),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}