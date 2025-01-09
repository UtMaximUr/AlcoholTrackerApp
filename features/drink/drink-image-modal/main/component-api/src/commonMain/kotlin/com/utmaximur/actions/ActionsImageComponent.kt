package com.utmaximur.actions

import com.utmaximur.core.decompose.ComposeDialogComponent
import com.utmaximur.media.PlatformFile


interface ActionsImageComponent : ComposeDialogComponent {

    fun handleFile(platformFile: PlatformFile)

    fun handleFiles(platformFiles: List<PlatformFile>)

    fun onDeleteFileClick()
}