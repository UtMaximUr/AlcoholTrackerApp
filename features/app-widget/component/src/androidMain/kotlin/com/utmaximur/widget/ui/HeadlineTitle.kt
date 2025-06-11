package com.utmaximur.widget.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.layout.fillMaxWidth
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import features.appwidget.component.R

@Composable
internal fun HeadlineTitle() {
    val context = LocalContext.current
    Text(
        modifier = GlanceModifier.fillMaxWidth(),
        text = context.getString(R.string.title_spent),
        style = TextStyle(
            color = GlanceTheme.colors.primary,
            fontSize = TextUnit(16f, TextUnitType.Sp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    )
}