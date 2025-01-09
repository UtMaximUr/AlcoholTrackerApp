package com.utmaximur.splash.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.utmaximur.splash.SplashScreenComponent
import com.utmaximur.splash.ui.wave.WaveView
import org.jetbrains.compose.resources.stringResource
import splashScreen.resources.Res
import splashScreen.resources.app_name

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun SplashScreen(
    component: SplashScreenComponent
) {
    var visibleState by remember { mutableStateOf(false) }
    val appName = if (visibleState) stringResource(Res.string.app_name) else String()

    LaunchedEffect(Unit) {
        visibleState = !visibleState
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            WaveView()
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                        .animateContentSize(
                            animationSpec = tween(
                                delayMillis = 500,
                                durationMillis = 700,
                                easing = LinearOutSlowInEasing
                            ),
                            finishedListener = { _, _ ->
                                component.readyToLoad()
                            }
                        ),
                    text = appName,
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}