package com.utmaximur.feature_calculator.calculator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.utmaximur.feature_calculator.calculator.CalculatorViewModel
import com.utmaximur.feature_calculator.utils.*
import com.utmaximur.feature_calculator.R


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = hiltViewModel(),
    price: String,
    onDismiss: () -> Unit,
    onResult: (String) -> Unit
) {

    viewModel.setPriceValue(price)

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(6.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                CalculatorText(
                    viewModel = viewModel,
                    onResult = onResult
                )
                Row {
                    CalculatorButton(
                        modifier = Modifier.fillMaxWidth(0.75f),
                        idText = R.string.calc_ac,
                        onClick = { viewModel.acCalculation() }
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    CalculatorButton(
                        modifier = Modifier.fillMaxWidth(),
                        idText = R.string.calc_ok,
                        colors = ButtonDefaults.buttonColors(),
                        textColor = Color.White,
                        onClick = { onDismiss() }
                    )
                }
                Row {
                    CalculatorButton(
                        modifier = Modifier.weight(1f),
                        idText = R.string.calc_7,
                        onClick = { viewModel.setValue(KEY_7) }
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    CalculatorButton(
                        modifier = Modifier.weight(1f),
                        idText = R.string.calc_8,
                        onClick = { viewModel.setValue(KEY_8) }
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    CalculatorButton(
                        modifier = Modifier.weight(1f),
                        idText = R.string.calc_9,
                        onClick = { viewModel.setValue(KEY_9) }
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    CalculatorButton(
                        modifier = Modifier.weight(1f),
                        idText = R.string.calc_divide,
                        colors = ButtonDefaults.buttonColors(),
                        textColor = Color.White,
                        onClick = { viewModel.setCurrentAction(DIVISION) }
                    )
                }
                Row {
                    CalculatorButton(
                        modifier = Modifier.weight(1f),
                        idText = R.string.calc_4,
                        onClick = { viewModel.setValue(KEY_4) }
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    CalculatorButton(
                        modifier = Modifier.weight(1f),
                        idText = R.string.calc_5,
                        onClick = { viewModel.setValue(KEY_5) }
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    CalculatorButton(
                        modifier = Modifier.weight(1f),
                        idText = R.string.calc_6,
                        onClick = { viewModel.setValue(KEY_6) }
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    CalculatorButton(
                        modifier = Modifier.weight(1f),
                        idText = R.string.calc_multiply,
                        colors = ButtonDefaults.buttonColors(),
                        textColor = Color.White,
                        onClick = { viewModel.setCurrentAction(MULTIPLICATION) }
                    )
                }
                Row {
                    CalculatorButton(
                        modifier = Modifier.weight(1f),
                        idText = R.string.calc_1,
                        onClick = { viewModel.setValue(KEY_1) }
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    CalculatorButton(
                        modifier = Modifier.weight(1f),
                        idText = R.string.calc_2,
                        onClick = { viewModel.setValue(KEY_2) }
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    CalculatorButton(
                        modifier = Modifier.weight(1f),
                        idText = R.string.calc_3,
                        onClick = { viewModel.setValue(KEY_3) }
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    CalculatorButton(
                        modifier = Modifier.weight(1f),
                        idText = R.string.calc_minus,
                        colors = ButtonDefaults.buttonColors(),
                        textColor = Color.White,
                        onClick = { viewModel.setCurrentAction(SUBTRACTION) }
                    )
                }
                Row {
                    CalculatorButton(
                        modifier = Modifier.weight(1f),
                        idText = R.string.calc_0,
                        onClick = { viewModel.setValue(KEY_0) }
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    CalculatorButton(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        idText = R.string.calc_equally,
                        colors = ButtonDefaults.buttonColors(),
                        textColor = Color.White,
                        onClick = { viewModel.computeCalculation() }
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    CalculatorButton(
                        modifier = Modifier.weight(1f),
                        idText = R.string.calc_plus,
                        colors = ButtonDefaults.buttonColors(),
                        textColor = Color.White,
                        onClick = { viewModel.setCurrentAction(ADDITION) }
                    )
                }
            }
        }
    }
}