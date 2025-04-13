package com.utmaximur.money.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.design.RequestWidget
import com.utmaximur.design.extensions.endFade
import com.utmaximur.design.extensions.fadingEdge
import com.utmaximur.design.extensions.startFade
import com.utmaximur.design.ui.Carousel
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.design.ui.dots.DotsIndicator
import com.utmaximur.money.StatisticMoneyComponent
import features.statistic.money.main.Res
import features.statistic.money.main.statistic_spent
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun StatisticMoneyScreen(
    component: StatisticMoneyComponent,
    modifier: Modifier,
) {
    val state by component.model.collectAsState()

    ElevatedCardApp(
        modifier = modifier.fillMaxWidth(),
    ) {
        RequestWidget(
            state = state.requestUi,
            shimmerContentTemplate = { CountMoneyShimmer() },
        ) { statistics ->
            val pagerState = rememberPagerState(pageCount = { statistics.size })
            Column(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fadingEdge(startFade)
                    .fadingEdge(endFade),
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(Res.string.statistic_spent),
                )
                Carousel(
                    horizontalPagerModifier = Modifier.weight(1f),
                    pagerState = pagerState,
                    carouselContent = { index ->
                        CountMoneyItem(
                            statistic = statistics[index],
                        )
                    },
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
