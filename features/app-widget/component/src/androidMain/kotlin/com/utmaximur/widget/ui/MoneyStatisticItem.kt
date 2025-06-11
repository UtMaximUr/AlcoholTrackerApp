package com.utmaximur.widget.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle

@Composable
internal fun MoneyStatisticItem(
    title: String, value: String
) {
    Row {
        Text(
            modifier = GlanceModifier,
            text = title,
            style = TextStyle(
                color = GlanceTheme.colors.primary,
                fontSize = TextUnit(16f, TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            modifier = GlanceModifier.fillMaxWidth(),
            text = value,
            style = TextStyle(
                color = GlanceTheme.colors.primary,
                fontSize = TextUnit(16f, TextUnitType.Sp),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End
            )
        )
    }
}