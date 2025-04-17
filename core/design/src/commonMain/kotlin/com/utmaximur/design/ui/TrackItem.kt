package com.utmaximur.design.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
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
    imageAspectRatio: Float = 2.5f,
) {
    Box(
        modifier = Modifier.clickable(onClick = onItemClick),
        contentAlignment = Alignment.BottomStart,
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(imageAspectRatio),
            model = drinkPhoto,
            contentDescription = drinkName,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            InfoText(
                text = stringResource(
                    Res.string.calendar_count_drink,
                    drinkName,
                    quantity,
                ),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.tertiary,
            )
            DrinkSpecifications(
                volume = volume,
                degree = degree,
                totalPrice = totalPrice,
                currency = currency
            )
        }
    }
}

@Composable
private fun DrinkSpecifications(
    volume: Float,
    degree: Float,
    totalPrice: Float,
    currency: String,
) = Row(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    InfoText(text = stringResource(Res.string.volume, volume))
    VerticalDividerRow()
    InfoText(text = stringResource(Res.string.degree, degree))
    VerticalDividerRow()
    InfoText(text = stringResource(Res.string.currency, totalPrice, currency))
}

@Composable
private fun InfoText(
    text: Any,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = Color.White,
    maxLines: Int = 1,
) = Text(
    modifier = modifier,
    text = text.toString(),
    style = style,
    color = color,
    maxLines = maxLines,
    overflow = TextOverflow.Ellipsis,
)

@Composable
private inline fun VerticalDividerRow() = VerticalDivider(
    modifier = Modifier.height(16.dp),
    thickness = 2.dp,
    color = MaterialTheme.colorScheme.tertiary
)
