package com.utmaximur.splash.ui.wave

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameMillis

@Composable
fun StepFrame(callbackTime: Long = 30L, callback:(frameTime: Long) -> Unit): State<Long> {
    val millis = remember { mutableStateOf(0L) }
    var lastFrameTime: Long = 0
    LaunchedEffect(Unit) {
        val startTime = withFrameMillis { it }
        while (true) {
            withFrameMillis { frameTime ->
                millis.value = frameTime - startTime
                if (lastFrameTime == 0L || (frameTime - lastFrameTime) >= callbackTime) {
                    lastFrameTime = frameTime
                    callback.invoke(frameTime)
                }
            }
        }
    }
    return millis
}