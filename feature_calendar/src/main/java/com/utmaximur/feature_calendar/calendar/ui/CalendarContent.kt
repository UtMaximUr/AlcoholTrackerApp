package com.utmaximur.feature_calendar.calendar.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.utmaximur.feature_calendar.calendar.CalendarViewModel
import com.utmaximur.feature_calendar.utils.backCalendarClick
import com.utmaximur.feature_calendar.utils.nextCalendarClick
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalPagerApi::class)
@Composable

fun CalendarContent(
    viewModel: CalendarViewModel = hiltViewModel(),
    innerPadding: Dp,
    calendar: Calendar,
    onDayEmptyTracks: (Long) -> Unit,
    onDayClick: (Long) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchEvents(calendar.timeInMillis)
    }

    val monthState = remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    val yearState = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }

    val scope = rememberCoroutineScope()

    val pagerHeaderState: PagerState =
        rememberPagerState(initialPage = monthState.value)
    val pagerMonthState: PagerState =
        rememberPagerState(initialPage = monthState.value)

    Column(modifier = Modifier.padding(bottom = innerPadding)) {
        MonthHeader(
            yearState = yearState,
            pagerState = pagerHeaderState,
            onBackClick = {
                backCalendarClick(monthState.value, yearState.value) { month, year ->
                    monthState.value = month
                    yearState.value = year
                    scope.launch {
                        pagerHeaderState.scrollToPage(month)
                        pagerMonthState.scrollToPage(month)
                    }
                }
            },
            onNextClick = {
                nextCalendarClick(monthState.value, yearState.value) { month, year ->
                    monthState.value = month
                    yearState.value = year
                    scope.launch {
                        pagerHeaderState.scrollToPage(month)
                        pagerMonthState.scrollToPage(month)
                    }
                }
            }
        )
        WeekHeader()
        Box(modifier = Modifier.weight(1f)) {
            Month(
                yearState = yearState,
                pagerState = pagerMonthState,
                onDayClick = {
                    scope.launch {
                        if (viewModel.isTrackByDayEmpty(it)) {
                            onDayEmptyTracks(it)
                        } else {
                            onDayClick(it)
                        }
                    }
                }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            InfoText()
        }
    }
}
