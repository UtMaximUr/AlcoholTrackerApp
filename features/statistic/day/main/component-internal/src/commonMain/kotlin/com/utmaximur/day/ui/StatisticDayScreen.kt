package com.utmaximur.day.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.day.StatisticDayComponent
import com.utmaximur.design.RequestWidget
import com.utmaximur.design.ui.DotsIndicator
import com.utmaximur.design.ui.ElevatedCardApp


@Composable
internal fun StatisticDayScreen(
    component: StatisticDayComponent
) {
    val state by component.model.collectAsState()

    ElevatedCardApp(
        modifier = Modifier.fillMaxWidth()
    ) {
        RequestWidget(
            state = state.requestUi,
            shimmerContentTemplate = { DrinksDayShimmer() }
        ) { statistics ->
            val pagerState = rememberPagerState(pageCount = { statistics.size })
            Column(modifier = Modifier.padding(12.dp)) {
                HorizontalPager(
                    state = pagerState
                ) { index ->
                    DrinksDayItem(
                        dayStatistic = statistics[index]
                    )
                }
                DotsIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    totalDots = pagerState.pageCount,
                    selectedIndex = pagerState.currentPage
                )
            }
        }
    }
}