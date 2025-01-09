package com.utmaximur.calendar.ui.month

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.utmaximur.calendar.store.TracksData
import com.utmaximur.calendar.ui.calendar.CalendarState
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.design.RequestWidget
import com.utmaximur.domain.models.Track

@Composable
fun HorizontalMonth(
    modifier: Modifier,
    calendarState: CalendarState,
    requestUi: RequestUi<TracksData>,
    changeView: () -> Unit,
    dayContent: @Composable ColumnScope.(List<Track>) -> Unit
) {

    val pagerState = rememberPagerState(
        initialPage = calendarState.currentMonthIndex,
        pageCount = { calendarState.months.size }
    )

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }
            .collect(calendarState::scrollToMonth)
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { index ->
        MonthViewLayout(
            dateMatrix = calendarState.months[index].dateMatrix,
            currentMonth = calendarState.months[index].currentMonth.month,
            currentDate = calendarState.currentDate,
            onDateClick = calendarState::selectViewByDate,
            changeView = changeView,
            dayContent = { date ->
                RequestWidget(
                    state = requestUi,
                    content = { tracks -> dayContent(tracks[date].orEmpty()) }
                )
            }
        )
    }
}