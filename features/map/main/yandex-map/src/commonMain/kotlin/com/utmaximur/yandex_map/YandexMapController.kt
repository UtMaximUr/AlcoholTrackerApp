package com.utmaximur.yandex_map

import com.utmaximur.domain.map.PlaceMark
import com.utmaximur.yandex_map.clusters.MapClusterTapListener
import com.utmaximur.yandex_map.clusters.MapClusterViewUpdater
import com.utmaximur.yandex_map.configs.MapSettingsConfig
import com.utmaximur.yandex_map.mapObjects.MapPlaceMarkTapListener
import com.utmaximur.yandex_map.resources.MapIconProvider

/**
 * Класс для управления картой и её визуальными компонентами.
 */
internal expect class YandexMapController {
    /**
     * Обновляет данные на карте, заменяя текущий набор объектов.
     * @param places - Список объектов [PlaceMark] с геокоординатами и метаданными.
     * @throws IllegalArgumentException если список содержит некорректные координаты.
     */
    fun submitData(places: List<PlaceMark>)

    /**
     * Метод для применения темной темы.
     */
    fun applyNightMode()

    /**
     * Метод жизненного цикла. Должен вызываться при старте связанного компонента.
     * Инициализирует ресурсы карты и подписки.
     */
    fun onStart()

    /**
     * Метод жизненного цикла. Должен вызываться при остановке связанного компонента.
     * Освобождает ресурсы карты и останавливает фоновые процессы.
     */
    fun onStop()
    companion object {
        fun create(
            mapIconProvider: MapIconProvider,
            mapPlaceMarkTapListener: MapPlaceMarkTapListener,
            mapClusterViewUpdater: MapClusterViewUpdater,
            mapClusterTapListener: MapClusterTapListener,
            mapSettingsConfig: MapSettingsConfig,
            mapObjectListener: (PlaceMarkIds) -> Boolean,
        ): YandexMapController
    }
}