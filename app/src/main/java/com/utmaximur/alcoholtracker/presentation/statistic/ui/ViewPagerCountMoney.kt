package com.utmaximur.alcoholtracker.presentation.statistic.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.utmaximur.alcoholtracker.R

@ExperimentalPagerApi
@Composable
fun ViewPagerCountMoney(
    money: List<String>,
    pagerState: PagerState = rememberPagerState(),
    tabItems: Array<String> = stringArrayResource(id = R.array.statistic_period_array)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier.heightIn(32.dp, 64.dp),
            count = tabItems.size,
            state = pagerState
        ) { index ->
            CountMoneyItem(money[index], tabItems[index])
        }
        DotsIndicator(tabItems.size, pagerState)
    }
}

@Composable
fun CountMoneyItem(money: String, period: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(12.dp)
                .weight(1f),
            text = period,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = MaterialTheme.colors.onPrimary
        )
        Text(
            modifier = Modifier.padding(bottom = 12.dp, top = 12.dp),
            text = money,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = MaterialTheme.colors.onPrimary
        )
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(id = R.string.add_currency),
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = MaterialTheme.colors.onPrimary
        )
    }
}