package com.utmaximur.design.ui.dots

import androidx.compose.ui.graphics.Color


internal fun DotMetrics.sizeBy(state: DotState): Float {
    return when (state) {
        DotState.Selected -> activeDotSizePx
        DotState.Normal -> normalDotSizePx
        DotState.Small -> minDotSizePx
        DotState.Invisible -> 0f
    }
}

internal fun DotsConfig.colorBy(state: DotState): Color {
    return when (state) {
        DotState.Selected -> activeDotColor
        else -> dotColor
    }
}

internal fun getVisibleDotRange(
    dotCount: Int,
    currentPage: Int,
    pageCount: Int,
): Pair<Int, Int> {
    val halfDotCount = dotCount / 2
    return if (currentPage < pageCount / 2) {
        val firstVisible = (currentPage - halfDotCount).coerceAtLeast(0)
        firstVisible to (firstVisible + dotCount - 1)
    } else {
        val lastVisible = (currentPage + halfDotCount).coerceAtMost(pageCount - 1)
        (lastVisible - dotCount + 1) to lastVisible
    }
}

internal fun getDotStates(
    index: Int,
    pagerFractionInt: Int,
    pageCount: Int,
    adjustedDotCount: Int
): Pair<DotState, DotState> {
    val current = determineDotState(index, pagerFractionInt, pageCount, adjustedDotCount)
    val future = determineDotState(index, pagerFractionInt + 1, pageCount, adjustedDotCount)
    return current to future
}

private fun determineDotState(
    index: Int,
    currentPage: Int,
    pageCount: Int,
    dotCount: Int,
): DotState {

    val sidesDotCount = dotCount / 2
    val leftBound = currentPage - sidesDotCount
    val rightBound = currentPage + sidesDotCount

    val (firstVisible, lastVisible) = getVisibleDotRange(
        dotCount = dotCount,
        currentPage = currentPage,
        pageCount = pageCount,
    )

    return when {
        // Текущая страница всегда выделена
        currentPage == index -> DotState.Selected

        // Крайние точки, если текущая страница близка к началу или концу
        rightBound >= pageCount && index > currentPage - (dotCount - (pageCount - currentPage - 1)) -> DotState.Normal
        leftBound <= 0 && index < currentPage + (dotCount - currentPage) -> DotState.Normal

        // Точки рядом с текущей страницей
        index in (leftBound + 1) until rightBound -> DotState.Normal

        // Последняя точка, если она видима
        rightBound == index && index == pageCount - 1 -> DotState.Normal

        // Все точки видны, если их количество равно количеству страниц
        dotCount == pageCount -> DotState.Normal

        // Точки на краях, которые не попали в предыдущие условия
        index in firstVisible..lastVisible -> DotState.Small

        // Остальные точки невидимы
        else -> DotState.Invisible
    }
}