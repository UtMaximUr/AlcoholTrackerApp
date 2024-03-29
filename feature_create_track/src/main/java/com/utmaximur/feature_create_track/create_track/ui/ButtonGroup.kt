package com.utmaximur.feature_create_track.create_track.ui

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.utmaximur.feature_create_track.R
import com.utmaximur.feature_create_track.create_track.CreateTrackViewModel
import com.utmaximur.utils.formatDate
import java.util.*

@ExperimentalAnimationApi
@Composable
fun ButtonGroup(
    viewModel: CreateTrackViewModel,
    context: Context = LocalContext.current,
    calendar: Calendar = Calendar.getInstance()
) {

    val dateText = stringResource(id = R.string.add_date)
    val dateState = remember { mutableStateOf(dateText) }
    val visibleTodayState by viewModel.visibleTodayState.observeAsState(true)
    val datePickerState = remember { mutableStateOf(false) }

    viewModel.selectedDate.observeAsState().apply {
        value?.let { date -> dateState.value = date.formatDate(context) }
    }

    AnimatedVisibility(visible = datePickerState.value) {
        DatePicker(
            viewModel = viewModel,
            onDateSelected = {
                dateState.value = it.formatDate(context)
                viewModel.onDateChange(it)
            },
            onDismissRequest = {
                datePickerState.value = false
            })
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            SelectDayButton(
                text = dateState.value,
                onSelectDateClick = {
                    datePickerState.value = true
                }
            )
        }
        Spacer(modifier = Modifier.size(4.dp))
        AnimatedVisibility(
            visible = visibleTodayState,
            enter = expandVertically()
        ) {
            TodayButton(
                text = R.string.add_today,
                onTodayClick = {
                    dateState.value = calendar.timeInMillis.formatDate(context)
                    viewModel.onDateChange(calendar.timeInMillis)
                }
            )
        }
    }
}

@Composable
fun SelectDayButton(
    text: String,
    size: TextUnit = 16.sp,
    radius: Int = 50,
    onSelectDateClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(radius),
        onClick = { onSelectDateClick() }
    ) {
        Text(
            text = text,
            fontSize = size,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = Color.White
        )
    }
}

@Composable
fun TodayButton(
    text: Int,
    size: TextUnit = 16.sp,
    radius: Int = 50,
    onTodayClick: () -> Unit
) {
    OutlinedButton(
        shape = RoundedCornerShape(radius),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
        onClick = { onTodayClick() }
    ) {
        Text(
            text = stringResource(id = text),
            fontSize = size,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = MaterialTheme.colors.onPrimary,
        )
    }
}