package com.utmaximur.calendar.ui.week

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.utmaximur.calendar.store.TracksData
import com.utmaximur.calendar.ui.calendar.CalendarState
import com.utmaximur.calendar.ui.day.DayEmptyContent
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.design.RequestWidget
import com.utmaximur.domain.models.Track
import kotlinx.coroutines.launch

@Composable
fun HorizontalWeek(
    modifier: Modifier,
    calendarState: CalendarState,
    requestUi: RequestUi<TracksData>,
    dayContent: @Composable ColumnScope.(List<Track>) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = calendarState.dates.indexOf(calendarState.selectedDate),
        pageCount = { calendarState.dates.size }
    )

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }
            .collect(calendarState::scrollToDate)
    }

    Column(modifier = modifier.fillMaxSize()) {
        DateRangeLayout(
            calendarState = calendarState,
            onDateClick = { date ->
                coroutineScope.launch {
                    val index = calendarState.dates.indexOf(date)
                    pagerState.animateScrollToPage(index)
                }
            }
        )
        HorizontalPager(state = pagerState) { index ->
            RequestWidget(
                state = requestUi,
                emptyContentTemplate = { DayEmptyContent() }
            ) { tracks ->
                dayContent(
                    tracks[calendarState.dates[index]].orEmpty()
                )
            }
        }
    }
}