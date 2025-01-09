package com.utmaximur.calculator.ui


import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.utmaximur.design.extensions.bounceClick


@Composable
internal fun CalculatorButton(
    modifier: Modifier = Modifier,
    title: String,
    containerColor: Color = MaterialTheme.colorScheme.background,
    textColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier.bounceClick(),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        onClick = onClick
    ) {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}