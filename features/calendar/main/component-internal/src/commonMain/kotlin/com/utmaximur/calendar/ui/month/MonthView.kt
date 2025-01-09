package com.utmaximur.calendar.ui.month

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

@Immutable
object MonthCalendarDefaults {
    @Composable
    fun config() = MonthCalendarConfig(
        currentDateColor = MaterialTheme.colorScheme.tertiary,
        dateColor = MaterialTheme.colorScheme.primary
    )
}

@Composable
internal fun MonthViewLayout(
    dateMatrix: List<List<LocalDate>>,
    currentMonth: Month,
    currentDate: LocalDate,
    onDateClick: (LocalDate) -> Unit,
    changeView: () -> Unit,
    dayContent: @Composable ColumnScope.(LocalDate) -> Unit,
    config: MonthCalendarConfig = MonthCalendarDefaults.config()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        dateMatrix.forEach { rowDates ->
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                rowDates.forEach { date ->
                    Column(
                        modifier = Modifier
                            .alpha(
                                alpha = remember(date, currentMonth) {
                                    if (date.month == currentMonth) 1.0f else 0.5f
                                }
                            )
                            .weight(1f)
                            .background(color = MaterialTheme.colorScheme.primaryContainer)
                            .fillMaxHeight()
                            .clickable {
                                onDateClick(date)
                                changeView()
                            }
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = date.dayOfMonth.toString(),
                            style = MaterialTheme.typography.titleSmall,
                            color = config.dateTextColor(currentDate, date)
                        )
                        dayContent(date)
                    }
                }
            }
        }
    }
}