package com.utmaximur.calendar.ui.day

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import calendar.resources.Res
import calendar.resources.calendar_count_drink
import calendar.resources.currency
import calendar.resources.degree
import calendar.resources.volume
import coil3.compose.AsyncImage
import com.utmaximur.domain.models.Track
import org.jetbrains.compose.resources.stringResource

@Composable
fun TrackItem(
    track: Track,
    currency: String,
    onItemClick: (Long) -> Unit
) {
    Box(
        modifier = Modifier.clickable { onItemClick(track.id) },
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 0.75f),
            model = track.drink.photo,
            contentDescription = track.drink.name,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                text = stringResource(
                    Res.string.calendar_count_drink,
                    track.drink.name,
                    track.quantity
                ),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(text = stringResource(Res.string.volume, track.volume))
                TextField(text = stringResource(Res.string.degree, track.degree))
                TextField(
                    text = stringResource(
                        Res.string.currency,
                        track.totalPrice,
                        currency
                    )
                )
            }
        }
    }
}

@Composable
private fun TextField(
    text: Any,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = Color.White
) {
    Text(
        modifier = modifier,
        text = text.toString(),
        style = style,
        color = color
    )
}