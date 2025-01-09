package com.utmaximur.calculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.utmaximur.calculator.CalculatorItem

@Composable
internal fun CalculatorRow(
    maxRowCount: Int,
    horizontalSpace: Dp = 6.dp,
    items: List<CalculatorItem>,
    button: @Composable (Modifier, CalculatorItem, Color, Color) -> Unit
) {
    val correctionWeight = horizontalSpace.value / 1000
    val defaultWeight = 1f
    val defaultElementWeight = defaultWeight / maxRowCount

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(horizontalSpace)
    ) {
        items.forEachIndexed { index, item ->
            val weight = when {
                items.size in 1 until maxRowCount -> when {
                    index == 0 -> defaultElementWeight * (maxRowCount - items.size.dec()) + correctionWeight
                    else -> defaultElementWeight - correctionWeight / items.size.dec()
                }

                else -> defaultWeight
            }
            val modifier = Modifier.weight(weight)
            val containerColor = when {
                item.isMainAction -> MaterialTheme.colorScheme.tertiary
                else -> MaterialTheme.colorScheme.background
            }
            val textColor = when {
                item.isMainAction -> Color.White
                else -> MaterialTheme.colorScheme.primary
            }

            button(modifier, item, containerColor, textColor)
        }
    }
}