package com.utmaximur.createTrack.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.domain.models.Drink
import createTrack.resources.Res
import createTrack.resources.cd_delete
import createTrack.resources.ic_delete_button
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ItemDrink(
    drink: Drink,
    onDeleteClick: (Long) -> Unit
) {

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
        AnimatedVisibility(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.TopEnd),
            visible = drink.isUserCreated
        ) {
            IconButton(onClick = { onDeleteClick(drink.id) }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_delete_button),
                    contentDescription = stringResource(Res.string.cd_delete)
                )
            }
        }
    }
}