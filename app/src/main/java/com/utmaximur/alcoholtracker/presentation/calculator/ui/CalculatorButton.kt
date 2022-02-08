package com.utmaximur.alcoholtracker.presentation.calculator.ui

import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.utmaximur.alcoholtracker.R

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    idText: Int,
    colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.background_color)),
    idTextColor: Int = R.color.text_color,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = colors,
        onClick = { onClick() }
    ) {
        Text(
            text = stringResource(id = idText),
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = colorResource(id = idTextColor)
        )
    }
}