package com.utmaximur.alcoholtracker.presentation.main.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = AccentColorDark,
    background = BackgroundDark,
    surface = Color(0xFF121212),
    onPrimary = TextColorDark
)

private val LightColorPalette = lightColors(
    primary = AccentColorLight,
    background = BackgroundLight,
    surface = Color.White,
    onPrimary = TextColorLight
)

@Composable
fun AlcoholTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}