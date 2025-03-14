package com.utmaximur.design.ui.dots

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.util.lerp
import kotlin.math.roundToInt

/**
 *
 * Компонент DotsIndicator представляет собой индикатор точек, который используется для визуализации
 * текущей позиции в наборе страниц (например, в карусели или постраничной навигации).
 * Он поддерживает анимацию изменения размера и цвета точек в зависимости от текущей позиции.
 *
 * Компонент использует линейную интерполяцию [lerp] для плавного изменения размера и цвета точек.
 * Производительность: Использование [Canvas] обеспечивает высокую производительность даже при большом количестве точек.
 * Кастомизация: Все параметры точек (размеры, цвета, отступы) можно настроить через [DotsConfig].
 *
 * @param modifier: Modifier - Модификатор для настройки внешнего вида и поведения компонента.
 * @param pageCount: Int - Общее количество страниц (точек), которые нужно отобразить.
 * @param currentPageFraction: State<Float> - Текущая позиция страницы в виде дробного числа.
 * @param  config: DotsConfig - Конфигурация для настройки внешнего вида точек. Включает размеры, цвета и отступы.
 *
 * Source:https://github.com/platacard/PagerIndicator
 */

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPageFraction: State<Float>,
    config: DotsConfig = DotsDefaults.config(),
) {
    val density = LocalDensity.current
    val metrics = remember(config) { DotMetrics(config, density) }

    val adjustedDotCount = remember(pageCount, config.dotCount) {
        if (config.dotCount >= pageCount) pageCount
        else if (config.dotCount % 2 == 0) config.dotCount - 1 else config.dotCount
    }

    val mainAxisSize = remember(adjustedDotCount, config) {
        config.activeDotSize * adjustedDotCount + config.spacing * (adjustedDotCount - 1)
    }

    Canvas(
        modifier = modifier
            .width(mainAxisSize)
            .height(config.activeDotSize)
    ) {
        val pagerFraction = currentPageFraction.value

        val itemsCount = (pagerFraction - adjustedDotCount / 2)
            .coerceIn(0f, pageCount - adjustedDotCount.toFloat())

        val dotStepPx = metrics.activeDotSizePx + metrics.spacingPx
        val scroll = -itemsCount * dotStepPx

        val (firstVisible, lastVisible) = getVisibleDotRange(
            dotCount = adjustedDotCount,
            currentPage = pagerFraction.roundToInt(),
            pageCount = pageCount,
        ).let { (first, second) ->
            (first - 1).coerceAtLeast(0) to (second + 1).coerceAtMost(pageCount - 1)
        }

        translate(left = scroll) {
            for (i in firstVisible..lastVisible) {
                val dotStart = i * dotStepPx
                val pagerFractionInt = pagerFraction.toInt()
                val scrollFraction = pagerFraction - pagerFractionInt

                val (currentState, futureState) = getDotStates(
                    index = i,
                    pagerFractionInt = pagerFractionInt,
                    pageCount = pageCount,
                    adjustedDotCount = adjustedDotCount
                )

                val targetDotSize =
                    lerp(metrics.sizeBy(currentState), metrics.sizeBy(futureState), scrollFraction)
                val targetDotColor =
                    lerp(config.colorBy(currentState), config.colorBy(futureState), scrollFraction)

                drawDot(
                    position = dotStart + metrics.activeDotSizePx / 2f - targetDotSize / 2f,
                    dotSize = metrics.activeDotSizePx,
                    targetDotSize = targetDotSize,
                    color = targetDotColor
                )
            }
        }
    }
}

// Отрисовка точки
private fun DrawScope.drawDot(
    position: Float,
    dotSize: Float,
    targetDotSize: Float,
    color: Color
) {
    with(DotsPainter) {
        translate(left = position, top = dotSize / 2f - targetDotSize / 2f) {
            draw(size = Size(targetDotSize, targetDotSize), colorFilter = ColorFilter.tint(color))
        }
    }
}