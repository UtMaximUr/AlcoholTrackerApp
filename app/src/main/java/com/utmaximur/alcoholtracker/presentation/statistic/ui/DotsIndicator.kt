package com.utmaximur.alcoholtracker.presentation.statistic.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.utmaximur.alcoholtracker.R

@ExperimentalPagerApi
@Composable
fun DotsIndicator(
    totalDots: Int,
    pagerState: PagerState
) {
    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(bottom = 12.dp)

    ) {

        items(totalDots) { index ->
            if (index == pagerState.currentPage) {
                Image(
                    painter = painterResource(id = R.drawable.ic_dot_18dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    contentDescription = null
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_dot_default_18dp),
                    contentDescription = null
                )
            }
            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}