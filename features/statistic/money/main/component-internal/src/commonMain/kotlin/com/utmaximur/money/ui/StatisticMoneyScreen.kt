package com.utmaximur.money.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.design.RequestWidget
import com.utmaximur.design.ui.DotsIndicator
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.money.StatisticMoneyComponent
import money.resources.Res
import money.resources.statistic_spent
import org.jetbrains.compose.resources.stringResource


@Composable
internal fun StatisticMoneyScreen(
    component: StatisticMoneyComponent
) {
    val state by component.model.collectAsState()

    ElevatedCardApp (
        modifier = Modifier.fillMaxWidth()
    ) {
        RequestWidget(
            state = state.requestUi,
            shimmerContentTemplate = { CountMoneyShimmer() }
        ) { statistics ->
            val pagerState = rememberPagerState(pageCount = { statistics.size })
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    style = MaterialTheme.typography.titleMedium,
                    text = stringResource(Res.string.statistic_spent)
                )
                HorizontalPager(
                    state = pagerState
                ) { index ->
                    CountMoneyItem(
                        statistic = statistics[index]
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