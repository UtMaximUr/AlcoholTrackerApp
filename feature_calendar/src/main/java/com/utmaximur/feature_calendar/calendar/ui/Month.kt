package com.utmaximur.feature_calendar.calendar.ui


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.utmaximur.domain.entity.CalendarDay
import com.utmaximur.feature_calendar.calendar.CalendarViewModel
import com.utmaximur.feature_calendar.utils.*
import java.util.*

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun Month(
    viewModel: CalendarViewModel = hiltViewModel(),
    yearState: MutableState<Int>,
    calendar: Calendar = Calendar.getInstance(),
    pagerState: PagerState = rememberPagerState(),
    onDayClick: (Long) -> Unit
) {

    val context = LocalContext.current

    val eventsDayState = viewModel.tracks.observeAsState(listOf())

    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currentMonth = calendar.get(Calendar.MONTH)

    val selectedDateState =
        remember { mutableStateOf(CalendarDay(currentDay, currentMonth, yearState.value)) }

    HorizontalPager(
        count = 12,
        state = pagerState,
        userScrollEnabled = false
    ) { month ->
        LazyVerticalGrid(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            cells = GridCells.Fixed(7),
            verticalArrangement = Arrangement.Top,
            userScrollEnabled = false
        ) {
            items(getCountDayPreviousMonth(yearState.value, month)) { index ->
                Day(day = getDayPreviousMonth(yearState.value, month, index))
            }
            items(getCountDayCurrentMonth(yearState.value, month)) { index ->
                Day(
                    day = index + 1,
                    event = context.getEvent(
                        yearState.value,
                        month,
                        index + 1,
                        eventsDayState.value
                    ),
                    currentDay = getCurrentDay(yearState.value, month, selectedDateState.value),
                    isCurrentDayOfMonth = true
                ) {
                    selectedDateState.value = CalendarDay(it, month, yearState.value)
                    calendar.set(yearState.value, month, it)
                    onDayClick(calendar.timeInMillis)
                }
            }
            items(getCountDayNextMonth(yearState.value, month)) { index ->
                Day(day = index + 1)
            }
        }
    }
}