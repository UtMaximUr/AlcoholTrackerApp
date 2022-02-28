package com.utmaximur.feature_create_track.create_track.ui

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.utmaximur.utils.formatDatePicker
import java.util.*
import com.utmaximur.feature_create_track.R
import com.utmaximur.feature_create_track.create_track.CreateTrackViewModel

@Composable
fun DatePicker(
    viewModel: CreateTrackViewModel,
    onDateSelected: (Long) -> Unit,
    onDismissRequest: () -> Unit
) {
    val selDate = remember { mutableStateOf(Calendar.getInstance().timeInMillis) }

    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties()) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(size = 6.dp)
                )
        ) {
            Column(
                Modifier
                    .defaultMinSize(minHeight = 72.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.add_date),
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White
                )

                Spacer(modifier = Modifier.size(24.dp))

                Text(
                    text = selDate.value.formatDatePicker(LocalContext.current),
                    style = MaterialTheme.typography.h4,
                    color = Color.White
                )

                Spacer(modifier = Modifier.size(16.dp))
            }

            CustomCalendarView(viewModel, onDateSelected = {
                selDate.value = it
            })

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = stringResource(id = R.string.dialog_cancel),
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.primary
                    )
                }

                TextButton(
                    onClick = {
                        onDateSelected(selDate.value)
                        onDismissRequest()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.dialog_done),
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}

@Composable
fun CustomCalendarView(
    viewModel: CreateTrackViewModel,
    onDateSelected: (Long) -> Unit
) {
    val calendar = Calendar.getInstance()

    var calTheme = R.style.CustomCalendar
    var dateTheme = R.style.CustomDate
    var weekTheme = R.style.CustomWeek

    if (viewModel.isDarkTheme(isSystemInDarkTheme())) {
        calTheme = R.style.CustomCalendarDark
        dateTheme = R.style.CustomDateDark
        weekTheme = R.style.CustomWeekDark
    }

    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            CalendarView(android.view.ContextThemeWrapper(context, calTheme)).apply {
                dateTextAppearance = dateTheme
                weekDayTextAppearance = weekTheme
            }
        },
        update = { view ->
            view.maxDate = calendar.timeInMillis
            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                onDateSelected(calendar.timeInMillis)
                view.date = calendar.timeInMillis
            }
        }
    )
}