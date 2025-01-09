package com.utmaximur.root.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import root.resources.Res
import root.resources.tt_norms_bold
import root.resources.tt_norms_extra_bold
import root.resources.tt_norms_medium
import root.resources.tt_norms_regular

/**
 * Light default theme color scheme
 */

val LightDefaultColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    tertiary = Accent,
    primaryContainer = LightBackgroundPrimary,
    secondaryContainer = LightBackgroundTertiary,
    outline = LightOutlinePrimary,
    outlineVariant = LightDividerSecondary,
    surfaceTint = LightShadow,
    background = LightBackgroundSecondary
)

/**
 * Dark default theme color scheme
 */

val DarkDefaultColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    tertiary = Accent,
    primaryContainer = DarkBackgroundPrimary,
    secondaryContainer = DarkBackgroundTertiary,
    outline = DarkOutlinePrimary,
    outlineVariant = DarkDividerSecondary,
    surfaceTint = DarkShadow,
    background = DarkBackgroundSecondary
)

@Composable
fun AlcoholTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = when {
        darkTheme -> DarkDefaultColorScheme
        else -> LightDefaultColorScheme
    }

    val typography = Typography(
        headlineLarge = TextStyle(
            fontFamily = FontFamily(Font(Res.font.tt_norms_bold)),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        ),
        titleLarge = TextStyle(
            fontFamily = FontFamily(Font(Res.font.tt_norms_bold)),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        titleMedium = TextStyle(
            fontFamily = FontFamily(Font(Res.font.tt_norms_medium)),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        titleSmall = TextStyle(
            fontFamily = FontFamily(Font(Res.font.tt_norms_medium)),
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = FontFamily(Font(Res.font.tt_norms_medium)),
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        ),
        labelLarge = TextStyle(
            fontFamily = FontFamily(Font(Res.font.tt_norms_regular)),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        labelMedium = TextStyle(
            fontFamily = FontFamily(Font(Res.font.tt_norms_regular)),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily(Font(Res.font.tt_norms_regular)),
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    )

    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(6.dp),
        large = RoundedCornerShape(8.dp),
        extraLarge = RoundedCornerShape(12.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}