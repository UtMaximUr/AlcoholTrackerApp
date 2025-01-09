package com.utmaximur.media

import androidx.compose.runtime.Composable

@Composable
expect fun rememberFilePickerLauncher(
    type: FilePickerFileType = FilePickerFileType.All,
    selectionMode: FilePickerSelectionMode = FilePickerSelectionMode.Multiple,
    onResult: (PlatformFiles) -> Unit,
): FilePickerLauncher

class FilePickerLauncher(
    private val onLaunch: () -> Unit,
) {
    fun launch() = onLaunch()
}

