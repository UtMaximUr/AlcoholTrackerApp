package com.utmaximur.alcoholtracker.presentation.calculator.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.utmaximur.alcoholtracker.R

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    idText: Int,
    colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
    textColor: Color = MaterialTheme.colors.onPrimary,
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
            color = textColor
        )
    }
}