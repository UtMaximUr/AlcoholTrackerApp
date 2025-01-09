package com.utmaximur.design.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.utmaximur.design.extensions.bounceClick
import design.resources.Res
import design.resources.add_date
import design.resources.add_today
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun DateButtonGroup(
    selectedDate: String,
    onSelectDateClick: (String) -> Unit,
    onTodayClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        SelectDayButton(
            modifier = Modifier.weight(1f),
            text = selectedDate.ifEmpty { stringResource(Res.string.add_date) },
            onSelectDateClick = { onSelectDateClick(selectedDate) }
        )
        AnimatedVisibility(
            visible = selectedDate.isEmpty(),
            enter = expandVertically()
        ) {
            TodayButton(
                text = Res.string.add_today,
                onTodayClick = onTodayClick
            )
        }
    }
}

@Composable
internal fun SelectDayButton(
    modifier: Modifier,
    text: String,
    onSelectDateClick: () -> Unit
) {
    Button(
        modifier = modifier.bounceClick(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = Color.White
        ),
        onClick = { onSelectDateClick() }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
internal fun TodayButton(
    text: StringResource,
    onTodayClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier.bounceClick(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        onClick = { onTodayClick() }
    ) {
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.labelLarge
        )
    }
}