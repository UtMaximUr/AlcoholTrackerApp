package com.utmaximur.datePicker.ui

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.utmaximur.datePicker.DatePickerComponent
import com.utmaximur.design.extensions.bounceClick
import datePicker.resources.Res
import datePicker.resources.date_picker_continue
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DatePickerScreen(
    component: DatePickerComponent
) {
    val state by component.model.collectAsState()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.selectedDate
    )

    DatePickerDialog(
        onDismissRequest = component::dismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    component.handleSelectDate(datePickerState.selectedDateMillis)
                },
                modifier = Modifier.bounceClick(),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = stringResource(Res.string.date_picker_continue),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        content = {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedDayContainerColor = MaterialTheme.colorScheme.tertiary,
                    todayDateBorderColor = MaterialTheme.colorScheme.tertiary,
                    navigationContentColor = MaterialTheme.colorScheme.tertiary,

                )
            )
        }
    )
}