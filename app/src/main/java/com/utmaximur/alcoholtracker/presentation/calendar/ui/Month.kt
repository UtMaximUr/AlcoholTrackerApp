package com.utmaximur.alcoholtracker.presentation.calendar.ui


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
import com.utmaximur.alcoholtracker.presentation.calendar.CalendarViewModel
import com.utmaximur.alcoholtracker.util.formatDate
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
        remember { mutableStateOf(Triple(currentDay, currentMonth, yearState.value)) }

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
            calendar.set(yearState.value, month, 1)

            val dayOfWeek = calendar[Calendar.DAY_OF_WEEK] - calendar.firstDayOfWeek
            val countDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

            calendar.set(yearState.value, month - 1, 1)
            val countDayPreviousMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

            items(dayOfWeek) { index ->
                Day(day = countDayPreviousMonth - dayOfWeek + index + 1)
            }
            items(countDay) { index ->
                calendar.set(yearState.value, month, index + 1)
                Day(
                    day = index + 1,
                    event =  eventsDayState.value.firstOrNull {
                        it.date.formatDate(context) == calendar.timeInMillis.formatDate(context)
                    },
                    currentDay = if (selectedDateState.value.second == month && selectedDateState.value.third == yearState.value) selectedDateState.value.first else -1,
                    isCurrentDayOfMonth = true
                ) {
                    selectedDateState.value = Triple(it, month, yearState.value)
                    calendar.set(yearState.value, month, it)
                    onDayClick(calendar.timeInMillis)
                }
            }
            val countDayNextMonth = 7 - (dayOfWeek + countDay) % 7
            items(if (countDayNextMonth != 7) countDayNextMonth else 0) { index ->
                Day(day = index + 1)
            }
        }
    }
}