package com.utmaximur.widget

import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceTheme
import androidx.glance.material3.ColorProviders

val widgetDarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF393A42),
    tertiaryContainer = Color(0xFFD50000),
)

val widgetLightColorScheme = darkColorScheme(
    primary = Color(0xFF020614),
    primaryContainer = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFD50000),
)

@Composable
fun AppWidgetTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    val colors = when {
        darkTheme -> widgetDarkColorScheme
        else -> widgetLightColorScheme
    }
    GlanceTheme(
        colors = ColorProviders(colors),
        content = content,
    )
}