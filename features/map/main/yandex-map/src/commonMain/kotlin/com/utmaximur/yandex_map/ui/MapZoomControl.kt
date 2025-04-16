package com.utmaximur.yandex_map.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
internal fun MapZoomControl(
    zoomStep: Float = 0.25f,
    onZoomClick: (Float) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ZoomPressButton(label = "+", onPress = { onZoomClick(zoomStep) })
        ZoomPressButton(label = "-", onPress = { onZoomClick(-zoomStep) })
    }
}

@Composable
private fun ZoomPressButton(
    label: String,
    onPress: () -> Unit,
    intervalMillis: Long = 100 // Интервал между повторениями
) {
    var isPressed by remember { mutableStateOf(false) }
    val animatedBackgroundColor by animateColorAsState(
        targetValue = when {
            isPressed -> MaterialTheme.colorScheme.secondaryContainer
            else -> MaterialTheme.colorScheme.primaryContainer
        }
    )
    LaunchedEffect(isPressed) {
        while (isPressed) {
            onPress()
            delay(intervalMillis)
        }
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(MaterialTheme.shapes.large)
            .background(animatedBackgroundColor)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        runCatching {
                            isPressed = true
                            awaitRelease()
                            isPressed = false
                        }.onFailure {
                            isPressed = false
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            style = MaterialTheme.typography.labelLarge,
            text = label,
        )
    }
}