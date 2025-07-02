package com.utmaximur.alcohol_calculator.main_screen.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.utmaximur.design.extensions.bounceClick
import features.alcohol_calculator.main.Res
import features.alcohol_calculator.main.calculate
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun BottomBarContent(
    onClick: () -> Unit
) = TextButton(
    modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()
        .bounceClick(),
    shape = MaterialTheme.shapes.large,
    colors = ButtonDefaults.textButtonColors(
        containerColor = MaterialTheme.colorScheme.tertiary,
    ),
    onClick = onClick,
) {
    Text(
        text = stringResource(Res.string.calculate),
        style = MaterialTheme.typography.bodyMedium,
        color = Color.White,
    )
}