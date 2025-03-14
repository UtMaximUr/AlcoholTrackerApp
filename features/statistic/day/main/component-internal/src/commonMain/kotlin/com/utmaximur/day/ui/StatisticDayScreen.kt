package com.utmaximur.day.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.day.StatisticDayComponent
import com.utmaximur.design.RequestWidget
import com.utmaximur.design.extensions.endFade
import com.utmaximur.design.extensions.fadingEdge
import com.utmaximur.design.extensions.startFade
import com.utmaximur.design.ui.Carousel
import com.utmaximur.design.ui.dots.DotsIndicator
import com.utmaximur.design.ui.ElevatedCardApp


@Composable
internal fun StatisticDayScreen(
    component: StatisticDayComponent,
    modifier: Modifier
) {
    val state by component.model.collectAsState()

    ElevatedCardApp(
        modifier = modifier.fillMaxWidth()
    ) {
        RequestWidget(
            state = state.requestUi,
            shimmerContentTemplate = { DrinksDayShimmer() }
        ) { statistics ->
            val pagerState = rememberPagerState(pageCount = { statistics.size })
            Column(modifier = Modifier
                .padding(vertical = 12.dp)
                .fadingEdge(startFade)
                .fadingEdge(endFade)
            ) {
                Carousel(
                    horizontalPagerModifier = Modifier.weight(1f),
                    pagerState = pagerState,
                    carouselContent = { index ->
                        DrinksDayItem(
                            dayStatistic = statistics[index]
                        )
                    }
                )
                DotsIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    pageCount = pagerState.pageCount,
                    currentPageFraction = remember {
                        derivedStateOf {
                            pagerState.currentPage + pagerState.currentPageOffsetFraction
                        }
                    },
                )
            }
        }
    }
}