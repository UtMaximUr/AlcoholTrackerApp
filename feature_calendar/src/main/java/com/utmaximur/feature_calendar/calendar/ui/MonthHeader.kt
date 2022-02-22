package com.utmaximur.feature_calendar.calendar.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.utmaximur.feature_calendar.R
import com.utmaximur.feature_calendar.utils.getMonthAndYearDate

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MonthHeader(
    yearState: MutableState<Int>,
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(),
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationMonth(Icons.Default.KeyboardArrowLeft) {
            onBackClick()
        }
        HorizontalPager(
            modifier = Modifier
                .heightIn(32.dp, 64.dp)
                .weight(1f),
            count = 12,
            state = pagerState,
            userScrollEnabled = false
        ) { index ->
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = yearState.value.getMonthAndYearDate(LocalContext.current, index),
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                )
            }
        }
        NavigationMonth(Icons.Default.KeyboardArrowRight) {
            onNextClick()
        }
    }
}

@Composable
fun NavigationMonth(
    icon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .fillMaxWidth(0.2f),
        onClick = { onClick() }
    ) {
        Image(
            imageVector = icon,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
            contentDescription = null,
        )
    }
}