package com.utmaximur.media

import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberFilePickerLauncher(
    type: FilePickerFileType,
    selectionMode: FilePickerSelectionMode,
    onResult: (PlatformFiles) -> Unit
): FilePickerLauncher {
    return when (selectionMode) {
        FilePickerSelectionMode.Single -> {
            when (type) {
                FilePickerFileType.Image, FilePickerFileType.Video, FilePickerFileType.ImageVideo ->
                    pickSingleVisualMedia(
                        type = type,
                        onResult = onResult,
                    )
                FilePickerFileType.Folder ->
                    pickFolder(
                        onResult = onResult,
                    )
                else ->
                    pickSingleFile(
                        type = type,
                        onResult = onResult,
                    )
            }
        }
        FilePickerSelectionMode.Multiple -> {
            when (type) {
                FilePickerFileType.Image, FilePickerFileType.Video, FilePickerFileType.ImageVideo ->
                    pickMultipleVisualMedia(
                        type = type,
                        onResult = onResult,
                    )
                FilePickerFileType.Folder ->
                    pickFolder(
                        onResult = onResult,
                    )
                else ->
                    pickMultipleFiles(
                        type = type,
                        onResult = onResult,
                    )
            }
        }
    }
}

@Composable
private fun pickSingleVisualMedia(
    type: FilePickerFileType,
    onResult: (PlatformFiles) -> Unit,
): FilePickerLauncher {
    val context = LocalContext.current
    val mediaPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                uri?.let { onResult(listOf(PlatformFile(it, context))) }
            },
        )

    return remember {
        FilePickerLauncher(
            onLaunch = {
                mediaPickerLauncher.launch(
                    type.toPickVisualMediaRequest(),
                )
            }
        )
    }
}

@Composable
private fun pickMultipleVisualMedia(
    type: FilePickerFileType,
    onResult: (PlatformFiles) -> Unit,
): FilePickerLauncher {
    val context = LocalContext.current
    val mediaPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(),
            onResult = { uriList ->
                val fileList =
                    uriList.map { uri ->
                        PlatformFile(uri, context)
                    }
                onResult(fileList)
            },
        )

    return remember {
        FilePickerLauncher(
            onLaunch = {
                mediaPickerLauncher.launch(
                    type.toPickVisualMediaRequest(),
                )
            },
        )
    }
}

@Composable
private fun pickSingleFile(
    type: FilePickerFileType,
    onResult: (PlatformFiles) -> Unit,
): FilePickerLauncher {
    val context = LocalContext.current
    val filePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
            onResult = { uri ->
                uri?.let { onResult(listOf(PlatformFile(it, context))) }
            },
        )

    return remember {
        FilePickerLauncher(
            onLaunch = {
                val mimeTypeMap = MimeTypeMap.getSingleton()

                filePickerLauncher.launch(
                    if (type is FilePickerFileType.Extension)
                        type.value.mapNotNull { mimeTypeMap.getMimeTypeFromExtension(it) }.toTypedArray()
                    else
                        type.value.toList().toTypedArray()
                )
            },
        )
    }
}
@Composable
private fun pickMultipleFiles(
    type: FilePickerFileType,
    onResult: (PlatformFiles) -> Unit,
): FilePickerLauncher {
    val context = LocalContext.current
    val filePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenMultipleDocuments(),
            onResult = { uriList ->
                val fileList =
                    uriList.map { uri ->
                        PlatformFile(uri, context)
                    }
                onResult(fileList)
            },
        )

    return remember {
        FilePickerLauncher(
            onLaunch = {
                val mimeTypeMap = MimeTypeMap.getSingleton()

                filePickerLauncher.launch(
                    if (type is FilePickerFileType.Extension)
                        type.value.mapNotNull { mimeTypeMap.getMimeTypeFromExtension(it) }.toTypedArray()
                    else
                        type.value.toList().toTypedArray()
                )
            },
        )
    }
}

@Composable
private fun pickFolder(
    onResult: (PlatformFiles) -> Unit,
): FilePickerLauncher {
    val context = LocalContext.current
    val singlePhotoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocumentTree(),
            onResult = { uri ->
                uri?.let { onResult(listOf(PlatformFile(it, context))) }
            },
        )

    return remember {
        FilePickerLauncher(
            onLaunch = {
                singlePhotoPickerLauncher.launch(null)
            },
        )
    }
}

private fun FilePickerFileType.toPickVisualMediaRequest(): PickVisualMediaRequest {
    return when (this) {
        FilePickerFileType.Image -> PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        FilePickerFileType.Video -> PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
        else -> PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
    }
}
