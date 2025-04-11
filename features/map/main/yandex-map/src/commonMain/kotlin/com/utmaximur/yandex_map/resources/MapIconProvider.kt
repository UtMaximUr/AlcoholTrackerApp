package com.utmaximur.yandex_map.resources

import androidx.compose.ui.graphics.Color

internal expect class MapIconProvider(
    placeMarkImageProvider: ImageProvider,
    clusterImageProvider: ImageProvider,
    clusterTextColor: Color
) {
    fun getClusterIcon(clusterSize: Int): MapIcon
    fun getIcon(): MapIcon
}

internal expect class MapIcon