package com.utmaximur.yandex_map.configs

/**
 * Конфигурация настроек карты для управления визуальным представлением и поведением.
 *
 * @property isDarkTheme Включение тёмной темы карты.
 * @property defaultClusterRadius Радиус группировки маркеров в кластеры (в пикселях).
 *   - Значение по умолчанию: `42.0`.
 *   - Минимальное значение: `10.0`.
 *   - При уменьшении радиуса кластеры формируются плотнее.
 *
 * @property defaultMinZoom Минимально допустимый уровень масштабирования карты.
 *   - По умолчанию: `35`
 *   - Минимальное значение: `0`(соответствует отображению всего мира на одной плитке).
 *
 * @property comfortableZoomLevel Уровень масштаба для "детального просмотра" (например, отдельные здания).
 *   - Значение по умолчанию: `15f`.
 *   - Рекомендуется использовать совместно с [commonZoomLevel].
 *
 * @property commonZoomLevel Уровень масштаба для "общего обзора" (например, город или район).
 *   - Значение по умолчанию: `10f`.
 *   - Должен быть меньше [comfortableZoomLevel].
 *
 * ### Ограничения:
 * - При некорректных значениях (например, [defaultClusterRadius] < 10) используется ближайшее валидное значение.
 * - Уровни масштаба ([defaultMinZoom], [comfortableZoomLevel], [commonZoomLevel]) должны быть согласованы:
 *   - `defaultMinZoom <= commonZoomLevel <= comfortableZoomLevel`.
 *
 * @throws IllegalArgumentException При несовместимых значениях (например, [commonZoomLevel] > [comfortableZoomLevel]).
 */
data class MapSettingsConfig(
    val isDarkTheme: Boolean = false,
    val defaultClusterRadius: Double = 42.0,
    val defaultMinZoom: Int = 35,
    val comfortableZoomLevel: Float = 15f,
    val commonZoomLevel: Float = 10f
)