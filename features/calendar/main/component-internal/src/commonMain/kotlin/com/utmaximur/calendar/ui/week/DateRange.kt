package com.utmaximur.calendar.ui.week

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.utmaximur.calendar.ui.calendar.CalendarState
import kotlinx.datetime.LocalDate

@Immutable
object DateRangeDefaults {
    @Composable
    fun config() = DateRangeConfig(
        currentDateActiveBackgroundColor = MaterialTheme.colorScheme.tertiary,
        currentDateInactiveBackgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
        selectedDateBackgroundColor = MaterialTheme.colorScheme.tertiary,
        selectedDateTextColor = Color.White,
        unselectedDateTextColor = MaterialTheme.colorScheme.primary,
        heightContainer = 40.dp,
        horizontalPadding = 8.dp,
        topPadding = 0.dp,
        bottomPadding = 8.dp
    )
}

@Composable
internal fun DateRangeLayout(
    calendarState: CalendarState,
    onDateClick: (LocalDate) -> Unit,
    config: DateRangeConfig = DateRangeDefaults.config()
) {

    val pagerState = rememberPagerState(
        initialPage = calendarState.weekNumber,
        pageCount = { calendarState.datesByWeek.size }
    )

    LaunchedEffect(Unit) {
        snapshotFlow { calendarState.weekNumber }
            .collect(pagerState::animateScrollToPage)
    }

    HorizontalPager(state = pagerState) { index ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(bottom = config.bottomPadding, top = config.topPadding)
                .height(config.heightContainer)
                .padding(horizontal = config.horizontalPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            calendarState.datesByWeek[index].forEach { date ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(config.heightContainer)
                        .background(
                            color = config.dateBackgroundColor(
                                calendarState.currentDate,
                                calendarState.selectedDate,
                                date
                            ),
                            shape = CircleShape
                        )
                        .clickable { onDateClick(date) }
                        .fillMaxHeight(),
                ) {
                    Text(
                        text = date.dayOfMonth.toString(),
                        style = MaterialTheme.typography.titleSmall,
                        color = config.dateTextColor(calendarState.currentDate, calendarState.selectedDate, date)
                    )
                }
            }
        }
    }
}