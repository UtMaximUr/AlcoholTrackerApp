package com.utmaximur.map.ui

import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.utmaximur.design.button.AddFloatingActionButton
import com.utmaximur.map.MapComponent
import com.utmaximur.yandex_map.configs.MapIconsConfig
import com.utmaximur.yandex_map.configs.MapSettingsConfig
import com.utmaximur.yandex_map.YandexMapContent
import map.resources.Res
import map.resources.ic_cluster
import map.resources.ic_cocktail_pin

@Composable
internal fun MapScreen(
    modifier: Modifier,
    component: MapComponent,
) {
    val state by component.model.collectAsState()

    Scaffold(
        modifier = modifier,
        content = { _ ->
            YandexMapContent(
                places = state.places,
                mapSettingsConfig = MapSettingsConfig(
                    isDarkTheme = state.isDarkTheme
                ),
                mapIconsConfig = MapIconsConfig(
                    placeMarkIcon = Res.drawable.ic_cocktail_pin,
                    clusterIcon = Res.drawable.ic_cluster,
                    clusterTextColor = Color.White
                ),
                mapObjectListener = { id ->
                    component.onMapObjectClick(id)
                    true
                },
                mapClusterListener = { ids->
                    component.onMapObjectsClick(ids)
                    true
                }
            )
        },
        floatingActionButton = {
            AddFloatingActionButton(onClick = component::onCreateTrackClick)
        },
        floatingActionButtonPosition = FabPosition.EndOverlay,
    )
}
