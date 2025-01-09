package com.utmaximur.media

import androidx.compose.runtime.Composable

@Composable
expect fun rememberCameraManager(onResult: (PlatformFile) -> Unit): CameraManager

class CameraManager(
    private val onLaunch: () -> Unit
) {
    fun launch() = onLaunch()
}
