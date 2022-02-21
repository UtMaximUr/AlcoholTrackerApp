package com.utmaximur.feature_statistic.statistic.ui

import android.content.res.Resources
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.utmaximur.feature_statistic.R
import java.util.*

@ExperimentalPagerApi
@Composable
fun ViewPagerCountDrink(
    statistic: List<Int>,
    pagerState: PagerState = rememberPagerState(),
    resources: Resources = LocalContext.current.resources
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier.heightIn(32.dp, 64.dp),
            count = statistic.size,
            state = pagerState
        ) { index ->
            if (index == 0) {
                DrinksDayItem(
                    stringResource(
                        id = R.string.statistic_count_days,
                        resources.getQuantityString(
                            R.plurals.plurals_day,
                            statistic.first(),
                            statistic.first()
                        ),
                        Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR)
                    )
                )
            } else {
                DrinksDayItem(
                    stringResource(
                        id = R.string.statistic_count_days_no_drink,
                        resources.getQuantityString(
                            R.plurals.plurals_day,
                            statistic.last(),
                            statistic.last()
                        )
                    )
                )
            }
        }
        DotsIndicator(statistic.size, pagerState)
    }
}

@Composable
fun DrinksDayItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(12.dp),
            text = text,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = MaterialTheme.colors.onPrimary
        )
    }
}