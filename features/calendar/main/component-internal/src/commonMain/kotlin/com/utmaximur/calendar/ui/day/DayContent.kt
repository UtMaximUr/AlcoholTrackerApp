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
import com.utmaximur.design.ui.TrackItem
import com.utmaximur.domain.Track
import features.calendar.main.Res
import features.calendar.main.calendar_empty
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DayContent(
    tracks: List<Track>,
    currency: String,
    onItemClick: (Long) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tracks) { track ->
            TrackItem(
                drinkName = track.drink.name,
                drinkPhoto = track.drink.photo,
                currency = currency,
                quantity = track.quantity,
                volume = track.volume,
                degree = track.degree,
                totalPrice = track.totalPrice,
                onItemClick = { onItemClick(track.id) },
            )
        }
    }
}

@Composable
internal fun DayEmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(Res.string.calendar_empty),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}
