package com.utmaximur.alcoholtracker.presentation.calendar.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.utmaximur.alcoholtracker.R
import java.util.*

@Composable
fun WeekHeader(
    modifier: Modifier = Modifier,
) {

    val calendar = Calendar.getInstance()
    val sortedWeekDay = if (calendar.firstDayOfWeek == Calendar.MONDAY) {
        stringArrayResource(id = R.array.calendar_week_days_array)
    } else {
        stringArrayResource(id = R.array.calendar_week_days_array).copyOfRange(6, 0)
    }

    Row(modifier = modifier.padding(12.dp)) {
        sortedWeekDay.forEach { day ->
            Text(
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                text = day,
                modifier = modifier
                    .weight(1f)
                    .wrapContentHeight(),
                color = if (day == stringResource(id = R.string.material_calendar_sunday)
                    || day == stringResource(id = R.string.material_calendar_saturday)
                )
                   MaterialTheme.colors.primary// colorResource(id = R.color.accent_color)
                else MaterialTheme.colors.onPrimary//colorResource(id = R.color.text_color)
            )
        }
    }
}