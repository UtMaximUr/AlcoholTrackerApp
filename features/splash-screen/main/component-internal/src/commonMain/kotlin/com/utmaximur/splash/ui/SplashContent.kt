package com.utmaximur.splash.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import splashScreen.resources.Res
import splashScreen.resources.app_name
import splashScreen.resources.empty

@Composable
internal fun SplashContent(readyToLoad: () -> Unit) {
    var appName: StringResource by remember { mutableStateOf(Res.string.empty) }
    LaunchedEffect(Unit) {
        appName = Res.string.app_name
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .animateContentSize(
                    animationSpec = tween(
                        delayMillis = delayMillis,
                        durationMillis = durationMillis,
                        easing = LinearOutSlowInEasing
                    ),
                    finishedListener = { _, _ ->
                        readyToLoad()
                    }
                ),
            text = stringResource(appName),
            style = MaterialTheme.typography.headlineLarge,
            fontSize = appNameFontSize.sp,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

private const val delayMillis = 500
private const val durationMillis = 700
private const val appNameFontSize = 32