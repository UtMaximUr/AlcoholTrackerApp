package com.utmaximur.calendar.ui.calendar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.utmaximur.calendar.models.CalendarView
import com.utmaximur.calendar.store.TracksData
import com.utmaximur.calendar.ui.day.DayContent
import com.utmaximur.calendar.ui.day.DayEmptyContent
import com.utmaximur.calendar.ui.day.DayMonthContent
import com.utmaximur.calendar.ui.month.HorizontalMonth
import com.utmaximur.calendar.ui.week.HorizontalWeek
import com.utmaximur.calendar.ui.week.WeekDayLayout
import com.utmaximur.core.mvi_mapper.RequestUi

@Composable
fun CalendarViewLayout(
    modifier: Modifier,
    calendarState: CalendarState,
    calendarView: CalendarView,
    requestUi: RequestUi<TracksData>,
    currency: String,
    onItemClick: (Long) -> Unit,
    changeView: () -> Unit
) {
    WeekDayLayout()

    Crossfade(
        targetState = calendarView,
        animationSpec = tween(500)
    ) { view ->
        when (view) {

            CalendarView.DAY_VIEW -> HorizontalWeek(
                modifier = modifier,
                calendarState = calendarState,
                requestUi = requestUi,
                dayContent = { tracks ->
                    AnimatedVisibility(tracks.isEmpty()) {
                        DayEmptyContent()
                    }
                    DayContent(
                        tracks = tracks,
                        currency = currency,
                        onItemClick = onItemClick
                    )
                }
            )

            CalendarView.MONTH_VIEW -> HorizontalMonth(
                modifier = modifier,
                calendarState = calendarState,
                requestUi = requestUi,
                changeView = changeView,
                dayContent = { tracks ->
                    DayMonthContent(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        tracks = tracks
                    )
                }
            )
        }
    }
}