package com.utmaximur.media

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.interop.LocalUIViewController
import kotlinx.cinterop.BetaInteropApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import platform.Foundation.NSData
import platform.Foundation.NSItemProvider
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.NSUUID
import platform.Foundation.dataWithContentsOfURL
import platform.Foundation.writeToURL
import platform.Photos.PHPhotoLibrary
import platform.PhotosUI.PHPickerConfiguration
import platform.PhotosUI.PHPickerConfigurationAssetRepresentationModeCurrent
import platform.PhotosUI.PHPickerFilter
import platform.PhotosUI.PHPickerResult
import platform.PhotosUI.PHPickerViewController
import platform.PhotosUI.PHPickerViewControllerDelegateProtocol
import platform.UIKit.UIDocumentPickerDelegateProtocol
import platform.UIKit.UIDocumentPickerViewController
import platform.UniformTypeIdentifiers.UTType
import platform.UniformTypeIdentifiers.UTTypeAudio
import platform.UniformTypeIdentifiers.UTTypeData
import platform.UniformTypeIdentifiers.UTTypeFolder
import platform.UniformTypeIdentifiers.UTTypeImage
import platform.UniformTypeIdentifiers.UTTypeVideo
import platform.darwin.NSObject
import kotlin.coroutines.resume

@Composable
actual fun rememberFilePickerLauncher(
    type: FilePickerFileType,
    selectionMode: FilePickerSelectionMode,
    onResult: (PlatformFiles) -> Unit
): FilePickerLauncher =
    if (type == FilePickerFileType.Image || type == FilePickerFileType.Video || type == FilePickerFileType.ImageVideo) {
        rememberImageVideoPickerLauncher(type, selectionMode, onResult)
    } else {
        rememberDocumentPickerLauncher(type, selectionMode, onResult)
    }

@Composable
private fun rememberDocumentPickerLauncher(
    type: FilePickerFileType,
    selectionMode: FilePickerSelectionMode,
    onResult: (PlatformFiles) -> Unit,
): FilePickerLauncher {
    val scope = rememberCoroutineScope()
    val currentUIViewController = LocalUIViewController.current
    val delegate =
        remember {
            object : NSObject(), UIDocumentPickerDelegateProtocol {
                override fun documentPicker(
                    controller: UIDocumentPickerViewController,
                    didPickDocumentAtURL: NSURL,
                ) {
                    scope.launch(Dispatchers.Main) {
                        val result =
                            if (type == FilePickerFileType.Folder)
                                listOf(PlatformFile(didPickDocumentAtURL))
                            else
                                listOfNotNull(
                                    didPickDocumentAtURL.createTempFile()?.let { tempUrl ->
                                        PlatformFile(tempUrl)
                                    }
                                )

                        onResult(result)
                    }
                }

                override fun documentPicker(
                    controller: UIDocumentPickerViewController,
                    didPickDocumentsAtURLs: List<*>,
                ) {
                    val dataList =
                        didPickDocumentsAtURLs.mapNotNull {
                            val nsUrl = it as? NSURL ?: return@mapNotNull null
                            if (type == FilePickerFileType.Folder)
                                PlatformFile(nsUrl)
                            else
                                nsUrl.createTempFile()?.let { tempUrl ->
                                    PlatformFile(
                                        tempUrl
                                    )
                                }
                        }

                    scope.launch(Dispatchers.Main) {
                        onResult(dataList)
                    }
                }

                override fun documentPickerWasCancelled(controller: UIDocumentPickerViewController) {
                    scope.launch(Dispatchers.Main) {
                        onResult(emptyList())
                    }
                }
            }
        }

    return remember(currentUIViewController) {
        FilePickerLauncher(
            onLaunch = {
                val pickerController =
                    createUIDocumentPickerViewController(
                        delegate = delegate,
                        type = type,
                        selectionMode = selectionMode,
                    )

                currentUIViewController.presentViewController(
                    pickerController,
                    true,
                    null,
                )
            },
        )
    }
}

@OptIn(BetaInteropApi::class)
@Composable
private fun rememberImageVideoPickerLauncher(
    type: FilePickerFileType,
    selectionMode: FilePickerSelectionMode,
    onResult: (List<PlatformFile>) -> Unit,
): FilePickerLauncher {
    val scope = rememberCoroutineScope()
    val currentUIViewController = LocalUIViewController.current

    val pickerDelegate = remember {
        object : NSObject(), PHPickerViewControllerDelegateProtocol {
            override fun picker(
                picker: PHPickerViewController,
                didFinishPicking: List<*>,
            ) {
                scope.launch {
                    val results = didFinishPicking
                        .mapNotNull {
                            val result = it as? PHPickerResult ?: return@mapNotNull null

                            async {
                                result.itemProvider.loadFileRepresentationForTypeIdentifierSuspend()
                            }
                        }
                        .awaitAll()
                        .filterNotNull()

                    withContext(Dispatchers.Main) {
                        onResult(results)
                    }
                }

                picker.dismissViewControllerAnimated(true, null)
            }
        }
    }

    return remember(currentUIViewController) {
        FilePickerLauncher(
            onLaunch = {
                val imagePicker =
                    createPHPickerViewController(
                        delegate = pickerDelegate,
                        type = type,
                        selectionMode = selectionMode,
                    )

                currentUIViewController.presentViewController(
                    imagePicker,
                    true,
                    null,
                )
            },
        )
    }
}

private suspend fun NSItemProvider.loadFileRepresentationForTypeIdentifierSuspend(): PlatformFile? =
    suspendCancellableCoroutine { continuation ->
        val progress = loadFileRepresentationForTypeIdentifier(
            typeIdentifier = registeredTypeIdentifiers.firstOrNull() as? String ?: UTTypeImage.identifier
        ) { url, error ->
            if (error != null) {
                continuation.resume(null)
                return@loadFileRepresentationForTypeIdentifier
            }

            continuation.resume(
                url?.createTempFile()?.let { tempUrl ->
                    PlatformFile(
                        tempUrl
                    )
                }
            )
        }

        continuation.invokeOnCancellation {
            progress.cancel()
        }
    }

private fun createUIDocumentPickerViewController(
    delegate: UIDocumentPickerDelegateProtocol,
    type: FilePickerFileType,
    selectionMode: FilePickerSelectionMode,
): UIDocumentPickerViewController {
    val contentTypes =
        if (type is FilePickerFileType.Extension)
            type.value
                .mapNotNull { extension ->
                    UTType.typeWithFilenameExtension(extension)
                }
                .ifEmpty { listOf(UTTypeData) }
        else
            type.value.mapNotNull { mimeType ->
                when (mimeType) {
                    FilePickerFileType.ImageContentType -> UTTypeImage
                    FilePickerFileType.VideoContentType -> UTTypeVideo
                    FilePickerFileType.AudioContentType -> UTTypeAudio
                    //FilePickerFileType.DocumentContentType -> UTTypeApplication
                    //FilePickerFileType.TextContentType -> UTTypeText
                    FilePickerFileType.AllContentType -> UTTypeData
                    FilePickerFileType.FolderContentType -> UTTypeFolder
                    else -> UTType.typeWithMIMEType(mimeType)
                }
            }

    val pickerController =
        UIDocumentPickerViewController(
            forOpeningContentTypes = contentTypes,
            asCopy = type != FilePickerFileType.Folder,
        )
    pickerController.delegate = delegate
    pickerController.allowsMultipleSelection = selectionMode == FilePickerSelectionMode.Multiple
    return pickerController
}


private fun createPHPickerViewController(
    delegate: PHPickerViewControllerDelegateProtocol,
    type: FilePickerFileType,
    selectionMode: FilePickerSelectionMode,
): PHPickerViewController {
    val configuration = PHPickerConfiguration(PHPhotoLibrary.sharedPhotoLibrary())
    val filterList = mutableListOf<PHPickerFilter>()
    when (type) {
        FilePickerFileType.Image ->
            filterList.add(PHPickerFilter.imagesFilter())

        FilePickerFileType.Video ->
            filterList.add(PHPickerFilter.videosFilter())

        else -> {
            filterList.add(PHPickerFilter.imagesFilter())
            filterList.add(PHPickerFilter.videosFilter())
        }
    }
    val newFilter = PHPickerFilter.anyFilterMatchingSubfilters(filterList.toList())
    configuration.filter = newFilter
    configuration.preferredAssetRepresentationMode = PHPickerConfigurationAssetRepresentationModeCurrent
    configuration.selectionLimit = if (selectionMode == FilePickerSelectionMode.Multiple) 0 else 1
    val picker = PHPickerViewController(configuration)
    picker.delegate = delegate
    return picker
}

private fun NSURL.createTempFile(): NSURL? {
    val extension = absoluteString
        ?.substringAfterLast('/')
        ?.substringAfterLast('.', "") ?: return null
    val data = NSData.dataWithContentsOfURL(this) ?: return null
    return NSURL.fileURLWithPath("${NSTemporaryDirectory()}/${NSUUID().UUIDString}.$extension").apply {
        data.writeToURL(this, true)
    }
}
