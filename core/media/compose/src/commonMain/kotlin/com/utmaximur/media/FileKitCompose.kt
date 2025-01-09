package com.utmaximur.media


sealed class FilePickerFileType(vararg val value: String) {
    data object Image : FilePickerFileType(ImageContentType)
    data object Video : FilePickerFileType(VideoContentType)
    data object ImageVideo : FilePickerFileType(ImageContentType, VideoContentType)
    data object Folder : FilePickerFileType(FolderContentType)
    data object All : FilePickerFileType(AllContentType)
    /**
     * Custom file extensions
     *
     * @param extensions List of extensions
     */
    data class Extension(val extensions: List<String>) : FilePickerFileType(*extensions.toTypedArray())

    companion object {
        const val FolderContentType = "folder"
        const val AudioContentType = "audio/*"
        const val ImageContentType = "image/*"
        const val VideoContentType = "video/*"
        const val AllContentType = "*/*"
    }
}

sealed class FilePickerSelectionMode {
    data object Single : FilePickerSelectionMode()
    data object Multiple : FilePickerSelectionMode()
}