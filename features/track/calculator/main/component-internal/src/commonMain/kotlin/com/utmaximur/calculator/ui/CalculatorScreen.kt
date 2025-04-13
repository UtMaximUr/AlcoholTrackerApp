package com.utmaximur.calculator.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.utmaximur.calculator.CalculatorComponent
import com.utmaximur.design.modal.ModalBottomSheetApp
import features.track.calculator.main.Res
import features.track.calculator.main.calculator
import features.track.calculator.main.cd_close
import features.track.calculator.main.ic_close_button
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CalculatorScreen(
    component: CalculatorComponent,
) {
    val state by component.model.collectAsState()

    ModalBottomSheetApp(
        onDismissRequest = component::dismiss
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(Res.string.calculator),
                    style = MaterialTheme.typography.titleLarge,
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
                input = state.input,
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
                            onClick = { component.handleCommand(item.action.execute()) },
                        )
                    },
                )
            }
        }
    }
}
