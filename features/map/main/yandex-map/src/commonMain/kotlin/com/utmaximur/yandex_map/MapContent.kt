package com.utmaximur.yandex_map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.utmaximur.domain.map.PlaceMark
import com.utmaximur.yandex_map.clusters.MapClusterTapListener
import com.utmaximur.yandex_map.clusters.MapClusterViewUpdater
import com.utmaximur.yandex_map.configs.MapIconsConfig
import com.utmaximur.yandex_map.configs.MapSettingsConfig
import com.utmaximur.yandex_map.mapObjects.MapPlaceMarkTapListener
import com.utmaximur.yandex_map.resources.MapIconProvider
import com.utmaximur.yandex_map.resources.imageProvider
import com.utmaximur.yandex_map.ui.MapBox
import com.utmaximur.yandex_map.ui.MapZoomControl

typealias PlaceMarkIds = List<Long>
typealias PlaceMarkId = Long

@Composable
fun YandexMapContent(
    places: List<PlaceMark>,
    mapIconsConfig: MapIconsConfig,
    mapSettingsConfig: MapSettingsConfig,
    mapObjectListener: (PlaceMarkId) -> Boolean,
    mapClusterListener: (PlaceMarkIds) -> Boolean
) {
    val placeMarkImageProvider = imageProvider(mapIconsConfig.placeMarkIcon)
    val clusterImageProvider = imageProvider(mapIconsConfig.clusterIcon)
    val mapIconProvider = remember {
        MapIconProvider(
            placeMarkImageProvider = placeMarkImageProvider,
            clusterImageProvider = clusterImageProvider,
            clusterTextColor = mapIconsConfig.clusterTextColor
        )
    }
    val mapPlaceMarkTapListener = remember { MapPlaceMarkTapListener(mapObjectListener) }
    val mapClusterViewUpdater = remember { MapClusterViewUpdater(mapIconProvider) }
    val mapClusterTapListener = remember { MapClusterTapListener(mapClusterListener) }
    val yandexMapController = remember {
        YandexMapController.create(
            mapIconProvider = mapIconProvider,
            mapPlaceMarkTapListener = mapPlaceMarkTapListener,
            mapClusterViewUpdater = mapClusterViewUpdater,
            mapClusterTapListener = mapClusterTapListener,
            mapSettingsConfig = mapSettingsConfig,
        )
    }
    LaunchedEffect(places) {
        yandexMapController.submitData(places)
    }
    MapBox(
        mapContent = {
            NativeMapContent(
                yandexMapController = yandexMapController
            )
        },
        mapControlContent = {
            MapZoomControl(
                onZoomClick = { zoomStep -> yandexMapController.zoom(zoomStep) }
            )
        }
    )
}

@Composable
internal expect fun NativeMapContent(
    yandexMapController: YandexMapController
)