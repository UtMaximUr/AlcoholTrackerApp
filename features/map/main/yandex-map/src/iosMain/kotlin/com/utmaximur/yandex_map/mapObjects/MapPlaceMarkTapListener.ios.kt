package com.utmaximur.yandex_map.mapObjects

import cocoapods.YandexMapsMobile.YMKMapObject
import cocoapods.YandexMapsMobile.YMKMapObjectTapListenerProtocol
import cocoapods.YandexMapsMobile.YMKPlacemarkMapObject
import cocoapods.YandexMapsMobile.YMKPoint
import com.utmaximur.domain.map.PlaceMark
import com.utmaximur.yandex_map.PlaceMarkIds
import kotlinx.cinterop.ExperimentalForeignApi
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
internal actual class MapPlaceMarkTapListener actual constructor(
    private val onPlaceMarkTap: ((PlaceMarkIds) -> Boolean)?
) : YMKMapObjectTapListenerProtocol, NSObject() {
    @OptIn(ExperimentalForeignApi::class)
    override fun onMapObjectTapWithMapObject(mapObject: YMKMapObject, point: YMKPoint): Boolean {
        return onPlaceMarkTap?.let { listener ->
            mapObject as YMKPlacemarkMapObject
            val placeMark = mapObject.userData as PlaceMark
            listener(listOf(placeMark.trackId))
        } == true
    }
}