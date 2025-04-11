package com.utmaximur.yandex_map.mapObjects

import com.utmaximur.yandex_map.PlaceMarkId

/**
 * Обрабатывает клики по объектам карты, оповещая о нажатии на PlaceMark.
 */
internal expect class MapPlaceMarkTapListener(
    onPlaceMarkTap: ((PlaceMarkId) -> Boolean)?
)