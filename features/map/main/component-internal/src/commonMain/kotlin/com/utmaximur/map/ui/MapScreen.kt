package com.utmaximur.map.ui

import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.utmaximur.design.button.AddFloatingActionButton
import com.utmaximur.map.MapComponent


@Composable
internal fun MapScreen(
    modifier: Modifier,
    component: MapComponent
) {
    val state by component.model.collectAsState()

    Scaffold(
        modifier = modifier,
        content = { _ ->
            MapContent(
                places = state.places,
                isDarkTheme = state.isDarkTheme,
                mapObjectClick = component::onMapObjectsClick
            )
        },
        floatingActionButton = {
            AddFloatingActionButton(onClick = component::onCreateTrackClick)
        },
        floatingActionButtonPosition = FabPosition.EndOverlay
    )
}