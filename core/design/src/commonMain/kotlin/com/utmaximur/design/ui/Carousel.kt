package com.utmaximur.design.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun Carousel(
    horizontalPagerModifier: Modifier = Modifier,
    config: CarouselConfig = CarouselDefaults.config(),
    pagerState: PagerState,
    carouselContent: @Composable (Int) -> Unit,
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    val lastIndex by remember(pagerState.currentPage) {
        derivedStateOf { pagerState.currentPage == pagerState.pageCount.dec() }
    }
    if (!isDragged && pagerState.pageCount > 1) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(config.carouselDelay)
                val page = if (lastIndex) 0 else pagerState.currentPage.inc()
                pagerState.animateScrollToPage(
                    page,
                    animationSpec = tween(if (lastIndex) config.animationSpec / 2 else config.animationSpec),
                )
            }
        }
    }
    HorizontalPager(
        modifier = horizontalPagerModifier,
        state = pagerState,
    ) { index ->
        carouselContent(index)
    }
}

@Immutable
object CarouselDefaults {
    @Composable
    fun config() = CarouselConfig(
        carouselDelay = 2_000,
        animationSpec = 1400,
    )
}

data class CarouselConfig(
    val carouselDelay: Long = 2_000,
    val animationSpec: Int = 1400,
) {
    init {
        require(animationSpec > 0, { "AnimationSpec must be > 0" })
    }
}
