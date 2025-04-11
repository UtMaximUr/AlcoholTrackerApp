package com.utmaximur.yandex_map.mapObjects

import com.utmaximur.yandex_map.PlaceMarkIds

/**
 * Обрабатывает клики по объектам карты, оповещая о нажатии на PlaceMark.
 */
internal expect class MapPlaceMarkTapListener(
    onPlaceMarkTap: ((PlaceMarkIds) -> Boolean)?
)