package com.utmaximur.feature_calculator.calculator.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.utmaximur.feature_calculator.R
import com.utmaximur.feature_calculator.calculator.CalculatorViewModel
import com.utmaximur.utils.empty

@Composable
fun CalculatorText(
    viewModel: CalculatorViewModel,
    onResult: (String) -> Unit
) {
    val currentValue by viewModel.currentValue.observeAsState(String.empty()).apply {
        onResult(value)
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = currentValue,
        color = MaterialTheme.colors.onPrimary,
        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
        textAlign = TextAlign.End
    )
}