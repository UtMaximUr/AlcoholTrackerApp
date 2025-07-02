package com.utmaximur.alcohol_calculator.main_screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.utmaximur.design.text.InnerShadowTextField
import com.utmaximur.design.ui.ElevatedCardApp
import features.alcohol_calculator.main.Res
import features.alcohol_calculator.main.degree
import features.alcohol_calculator.main.degree_symbol
import features.alcohol_calculator.main.drink
import features.alcohol_calculator.main.volume
import features.alcohol_calculator.main.volume_symbol
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DrinkItem(
    modifier: Modifier,
    index: Int,
    onVolumeChange: (String) -> Unit,
    onAlcoholPercentageChange: (String) -> Unit,
) {
    ElevatedCardApp(
        modifier = modifier,
        contentPaddingValues = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            text = stringResource(Res.string.drink, index),
            color = MaterialTheme.colorScheme.tertiary,
        )
        InnerShadowTextField(
            title = stringResource(Res.string.degree),
            suffixText = stringResource(Res.string.degree_symbol),
            keyboardType = KeyboardType.Decimal,
            onValueChange = onAlcoholPercentageChange,
        )
        InnerShadowTextField(
            title = stringResource(Res.string.volume),
            suffixText = stringResource(Res.string.volume_symbol),
            keyboardType = KeyboardType.Number,
            onValueChange = onVolumeChange,
        )
    }
}