package com.utmaximur.calendar.ui.week

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.calendar.models.daysOfWeekSortedBy
import com.utmaximur.calendar.models.firstDayOfWeek
import com.utmaximur.calendar.models.isDayOff
import com.utmaximur.calendar.models.localized

@Composable
internal fun WeekDayLayout() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 1.dp)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(top = 16.dp, bottom = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        daysOfWeekSortedBy(firstDayOfWeek()).forEach {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = it.localized(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = when {
                        it.isDayOff() -> MaterialTheme.colorScheme.tertiary
                        else -> MaterialTheme.colorScheme.primary
                    }
                )
            }
        }
    }
}