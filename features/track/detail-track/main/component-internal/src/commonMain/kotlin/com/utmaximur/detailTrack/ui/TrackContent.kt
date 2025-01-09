package com.utmaximur.detailTrack.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.utmaximur.design.text.InnerShadowTextField
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.domain.models.Track
import com.utmaximur.domain.models.TrackData
import detailTrack.resources.Res
import detailTrack.resources.add_degree
import detailTrack.resources.add_event
import detailTrack.resources.add_price
import detailTrack.resources.add_quantity
import detailTrack.resources.add_volume
import detailTrack.resources.cd_calculator
import detailTrack.resources.cd_event
import detailTrack.resources.cd_price
import detailTrack.resources.ic_calculate_white_24dp
import detailTrack.resources.ic_event_24dp
import detailTrack.resources.ic_local_bar_white_24dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun TrackContent(
    price: Float,
    trackData: TrackData.Builder,
    track: Track,
    onCalculatorClick: () -> Unit
) {
    LaunchedEffect(track) {
        trackData.setDrink(track.drink)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ElevatedCardApp {
            AsyncImage(
                modifier = Modifier.aspectRatio(4 / 3f),
                model = track.drink.photo,
                contentDescription = track.drink.name,
                contentScale = ContentScale.Crop
            )
        }

        ElevatedCardApp(
            contentPaddingValues = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            InnerShadowTextField(
                title = stringResource(Res.string.add_quantity),
                textValue = track.quantity,
                keyboardType = KeyboardType.Number,
                onValueChange = trackData::setQuantity
            )
            InnerShadowTextField(
                title = stringResource(Res.string.add_volume),
                textValue = track.volume,
                keyboardType = KeyboardType.Number,
                onValueChange = trackData::setVolume
            )
            InnerShadowTextField(
                title = stringResource(Res.string.add_degree),
                textValue = track.degree,
                keyboardType = KeyboardType.Number,
                onValueChange = trackData::setDegree
            )
            InnerShadowTextField(
                title = stringResource(Res.string.add_event),
                textValue = track.event,
                onValueChange = trackData::setEvent,
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_event_24dp),
                        contentDescription = stringResource(Res.string.cd_event),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            )
            InnerShadowTextField(
                title = stringResource(Res.string.add_price),
                textValue = price,
                keyboardType = KeyboardType.Number,
                onValueChange = trackData::setPrice,
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_local_bar_white_24dp),
                        contentDescription = stringResource(Res.string.cd_price),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable(onClick = onCalculatorClick),
                        painter = painterResource(Res.drawable.ic_calculate_white_24dp),
                        contentDescription = stringResource(Res.string.cd_calculator),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            )
        }
    }
}