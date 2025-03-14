package com.utmaximur.design.ui.dots

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
object DotsDefaults {
    @Composable
    fun config() = DotsConfig(
        normalDotSize = 10.dp,
        activeDotSize = 16.dp,
        minDotSize = 6.dp,
        spacing = 8.dp,
        activeDotColor = MaterialTheme.colorScheme.tertiary,
        dotColor = MaterialTheme.colorScheme.tertiary,
        dotCount = 9
    )
}

/**
 *
 * @param dotCount: Int - Количество отображаемых точек. Если точек больше, чем pageCount,
 *                        отображаются все страницы. Если точек меньше, количество точек
 *                        корректируется для обеспечения симметрии.
 * @param minDotSize: Dp - Минимальный размер точки (для неактивных точек на краях).
 * @param normalDotSize: Dp - Размер неактивных точек.
 * @param activeDotSize: Dp - Размер активной точки.
 * @param spacing: Dp - Расстояние между точками.
 * @param dotColor: Color - Цвет неактивных точек.
 * @param activeDotColor: Color - Цвет активной точки.
 *
 */

data class DotsConfig(
    val normalDotSize: Dp,
    val activeDotSize: Dp,
    val minDotSize: Dp,
    val spacing: Dp,
    val activeDotColor: Color,
    val dotColor: Color,
    val dotCount: Int,
)

/**
 *
 * Вспомогательный класс для предварительного вычисления и хранения метрик точек(размеров и отступов)
 * в пикселях. Используется для оптимизации расчетов в Compose-компонентах.
 *
 * @property minDotSizePx: Float - Минимальный размер точки (в пикселях).
 * @property normalDotSizePx: Float - Стандартный размер точки (в пикселях).
 * @property activeDotSizePx: Float - Размер активной точки (в пикселях).
 * @property spacingPx: Float - Расстояние между точками (в пикселях).
 */
@Immutable
internal class DotMetrics(config: DotsConfig, density: Density) {
    val minDotSizePx = with(density) { config.minDotSize.toPx() }
    val normalDotSizePx = with(density) { config.normalDotSize.toPx() }
    val activeDotSizePx = with(density) { config.activeDotSize.toPx() }
    val spacingPx = with(density) { config.spacing.toPx() }
}