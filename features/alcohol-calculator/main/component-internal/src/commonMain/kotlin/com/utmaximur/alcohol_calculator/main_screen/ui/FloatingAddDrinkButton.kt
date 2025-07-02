package com.utmaximur.alcohol_calculator.main_screen.ui

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.utmaximur.design.extensions.bounceClick
import features.alcohol_calculator.main.Res
import features.alcohol_calculator.main.add_drink
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun FloatingAddDrinkButton(
    onClick: () -> Unit
) = TextButton(
    modifier = Modifier.bounceClick(),
    shape = MaterialTheme.shapes.large,
    colors = ButtonDefaults.textButtonColors(
        containerColor = MaterialTheme.colorScheme.tertiary,
    ),
    onClick = onClick,
) {
    Text(
        text = stringResource(Res.string.add_drink),
        style = MaterialTheme.typography.bodyMedium,
        color = Color.White,
    )
}