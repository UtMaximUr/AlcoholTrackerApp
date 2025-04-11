package com.utmaximur.yandex_map.mapObjects

import com.utmaximur.domain.map.PlaceMark
import com.utmaximur.yandex_map.PlaceMarkIds
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.MapObjectTapListener

internal actual class MapPlaceMarkTapListener actual constructor(
    private val onPlaceMarkTap: ((PlaceMarkIds) -> Boolean)?
) : MapObjectTapListener {
    override fun onMapObjectTap(mapObject: MapObject, point: Point): Boolean {
        return onPlaceMarkTap?.let { listener ->
            mapObject as PlacemarkMapObject
            val placeMark = mapObject.userData as PlaceMark
            listener(listOf(placeMark.trackId))
        } == true
    }
}