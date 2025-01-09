package com.utmaximur.createTrack.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.domain.models.Drink

@Composable
fun ItemDrink(drink: Drink) {

    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = drink.photo,
            contentDescription = drink.name,
            contentScale = ContentScale.Crop
        )
        ElevatedCardApp(
            modifier = Modifier.padding(12.dp),
            shape = MaterialTheme.shapes.large,
            defaultElevation = 2.dp
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = drink.name,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}