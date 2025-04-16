package com.utmaximur.yandex_map

import com.utmaximur.domain.map.PlaceMark
import com.utmaximur.yandex_map.clusters.MapClusterTapListener
import com.utmaximur.yandex_map.clusters.MapClusterViewUpdater
import com.utmaximur.yandex_map.configs.MapSettingsConfig
import com.utmaximur.yandex_map.map.NativeMapView
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
     * Изменяет текущий уровень масштаба, используя заданный шаг масштабирования.
     *
     * Положительное значение [zoomStep] увеличивает масштаб (приближение), отрицательное — уменьшает (отдаление).
     * Например:
     * - `zoom(0.1f)` увеличит масштаб на 10%
     * - `zoom(-0.05f)` уменьшит масштаб на 5%
     *
     * @param zoomStep Шаг изменения масштаба (рекомендуемый диапазон: -0.5 до 0.5).
     *                 Нулевое значение игнорируется.
     */
    fun zoom(zoomStep: Float)

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

    /**
     * Возвращает экземпляр [NativeMapView], используемый для отображения карты.
     */
    fun getMapView() : NativeMapView

    companion object {
        fun create(
            mapIconProvider: MapIconProvider,
            mapPlaceMarkTapListener: MapPlaceMarkTapListener,
            mapClusterViewUpdater: MapClusterViewUpdater,
            mapClusterTapListener: MapClusterTapListener,
            mapSettingsConfig: MapSettingsConfig,
        ): YandexMapController
    }
}