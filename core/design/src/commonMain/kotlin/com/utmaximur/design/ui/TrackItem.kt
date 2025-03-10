package com.utmaximur.design.ui

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
import coil3.compose.AsyncImage
import design.resources.Res
import design.resources.calendar_count_drink
import design.resources.currency
import design.resources.degree
import design.resources.volume
import org.jetbrains.compose.resources.stringResource

@Composable
fun TrackItem(
    drinkName: String,
    drinkPhoto: String,
    currency: String,
    quantity: Int,
    volume: Float,
    degree: Float,
    totalPrice: Float,
    onItemClick: () -> Unit,
) {
    Box(
        modifier = Modifier.clickable(onClick = onItemClick),
        contentAlignment = Alignment.BottomStart,
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 0.75f),
            model = drinkPhoto,
            contentDescription = drinkName,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TextField(
                text = stringResource(
                    Res.string.calendar_count_drink,
                    drinkName,
                    quantity,
                ),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.tertiary,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                TextField(text = stringResource(Res.string.volume, volume))
                TextField(text = stringResource(Res.string.degree, degree))
                TextField(
                    text = stringResource(
                        Res.string.currency,
                        totalPrice,
                        currency,
                    ),
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
    color: Color = Color.White,
) {
    Text(
        modifier = modifier,
        text = text.toString(),
        style = style,
        color = color,
    )
}
