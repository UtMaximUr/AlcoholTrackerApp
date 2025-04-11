package com.utmaximur.yandex_map.configs

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource

/**
 * Конфигурация для кастомизации иконок маркеров и кластеров на карте.
 *
 * @property placeMarkIcon Ресурс иконки для одиночных меток на карте.
 *   - Используется для отображения отдельных точек интереса.
 *   - Пример: `DrawableResource(R.drawable.ic_place_mark)`.
 *
 * @property clusterIcon Ресурс иконки для кластеров (групп меток).
 *   - Отображается при группировке близко расположенных маркеров.
 *   - Пример: `DrawableResource(R.drawable.ic_cluster)`.
 *
 * @property clusterTextColor Цвет текста для отображения количества меток в кластере.
 *   - Рекомендуется использовать контрастный цвет относительно фона [clusterIcon].
 *   - Пример: `Color.BLACK` или `Color(0xFF2F4B78)`.
 *
 * ### Требования:
 * - Размеры иконок должны учитывать плотность экрана (предоставлять ресурсы для разных DPI).
 * - Иконка кластера должна содержать область для текста (рекомендуемый размер текстовой зоны: 60% от размера иконки).
 *
 * ### Особенности реализации:
 * - Для динамического изменения иконок используйте [MapIconProvider] с обновлённым [MapIconsConfig].
 * - Цвет применяется только к тексту кластера (не влияет на иконки меток).
 */
data class MapIconsConfig(
    val placeMarkIcon: DrawableResource,
    val clusterIcon: DrawableResource,
    val clusterTextColor: Color,
)