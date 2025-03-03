package com.utmaximur.tracksModal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.utmaximur.design.modal.ModalBottomSheetApp
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.design.ui.TrackItem
import com.utmaximur.tracksModal.TracksModalComponent


@Composable
internal fun TracksModalDialog(
    component: TracksModalComponent
) {
    val state by component.model.collectAsState()

    ModalBottomSheetApp(
        onDismissRequest = component::dismiss
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(state.tracks) { track ->
                ElevatedCardApp {
                    TrackItem(
                        drinkName = track.drink.name,
                        drinkPhoto = track.drink.photo,
                        currency = state.currency,
                        quantity = track.quantity,
                        volume = track.volume,
                        degree = track.degree,
                        totalPrice = track.totalPrice,
                        onItemClick = { component.navigateToDetailScreen(track.id) }
                    )
                }
            }
        }
    }
}