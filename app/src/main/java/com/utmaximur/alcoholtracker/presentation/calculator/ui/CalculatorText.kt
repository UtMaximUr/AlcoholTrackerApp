package com.utmaximur.alcoholtracker.presentation.calculator.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.calculator.CalculatorViewModel
import com.utmaximur.alcoholtracker.util.extension.empty

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
        color = colorResource(id = R.color.text_color),
        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
        textAlign = TextAlign.End
    )
}