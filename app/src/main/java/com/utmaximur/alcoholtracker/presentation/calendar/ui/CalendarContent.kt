package com.utmaximur.alcoholtracker.presentation.calendar.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.utmaximur.alcoholtracker.presentation.calendar.CalendarViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
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
        Timber.d("LaunchedEffect")
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
                if (monthState.value != 0) {
                    monthState.value = monthState.value - 1
                } else {
                    monthState.value = 11
                    yearState.value = yearState.value - 1
                }
                scope.launch {
                    pagerHeaderState.scrollToPage(monthState.value)
                    pagerMonthState.scrollToPage(monthState.value)
                }
            },
            onNextClick = {
                if (monthState.value != 11) {
                    monthState.value = monthState.value + 1
                } else {
                    monthState.value = 0
                    yearState.value = yearState.value + 1
                }
                scope.launch {
                    pagerHeaderState.scrollToPage(monthState.value)
                    pagerMonthState.scrollToPage(monthState.value)
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

    Timber.tag("calendar_debug_log").d("CalendarContent")
}
