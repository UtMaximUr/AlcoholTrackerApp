package com.utmaximur.datePicker.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.utmaximur.datePicker.DatePickerComponent
import com.utmaximur.design.extensions.bounceClick
import com.utmaximur.design.modal.ModalBottomSheetApp
import features.track.date_picker.main.Res
import features.track.date_picker.main.date_picker_continue
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DatePickerScreen(
    component: DatePickerComponent,
) {
    val state by component.model.collectAsState()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.selectedDate,
    )

    ModalBottomSheetApp(
        onDismissRequest = component::dismiss
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedDayContainerColor = MaterialTheme.colorScheme.tertiary,
                todayDateBorderColor = MaterialTheme.colorScheme.tertiary,
                navigationContentColor = MaterialTheme.colorScheme.tertiary,

                ),
        )
        TextButton(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .bounceClick(),
            shape = MaterialTheme.shapes.large,
            onClick = {
                component.handleSelectDate(datePickerState.selectedDateMillis)
            },
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
            ),
        ) {
            Text(
                text = stringResource(Res.string.date_picker_continue),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
            )
        }
    }
}
