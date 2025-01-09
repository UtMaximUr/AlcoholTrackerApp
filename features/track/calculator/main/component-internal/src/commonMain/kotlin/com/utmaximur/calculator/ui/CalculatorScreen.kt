package com.utmaximur.calculator.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import calculator.resources.Res
import calculator.resources.calculator
import calculator.resources.cd_close
import calculator.resources.ic_close_button
import com.utmaximur.calculator.CalculatorComponent
import com.utmaximur.design.ui.ElevatedCardApp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
internal fun CalculatorScreen(
    component: CalculatorComponent
) {
    val state by component.model.collectAsState()

    Dialog(
        onDismissRequest = component::dismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        ElevatedCardApp(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            contentPaddingValues = PaddingValues(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.calculator),
                    style = MaterialTheme.typography.titleLarge
                )
                Icon(
                    painter = painterResource(Res.drawable.ic_close_button),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(size = 24.dp)
                        .clickable(onClick = component::dismiss),
                    contentDescription = stringResource(Res.string.cd_close),
                )
            }
            CalculatorText(
                expression = state.expression,
                input = state.input
            )
            state.matrixItems.forEach { items ->
                CalculatorRow(
                    maxRowCount = state.matrixItems.maxBy { it.size }.size,
                    items = items,
                    button = { modifier, item, containerColor, textColor ->
                        CalculatorButton(
                            modifier = modifier,
                            containerColor = containerColor,
                            textColor = textColor,
                            title = item.title,
                            onClick = { component.handleCommand(item.action.execute()) }
                        )
                    }
                )
            }
        }
    }
}