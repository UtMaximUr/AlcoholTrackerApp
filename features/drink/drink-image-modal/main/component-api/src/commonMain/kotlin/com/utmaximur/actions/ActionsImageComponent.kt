package com.utmaximur.actions

import com.utmaximur.actions.store.ActionsImageStore
import com.utmaximur.core.decompose.ComposeDialogComponent
import com.utmaximur.media.PlatformFile
import kotlinx.coroutines.flow.StateFlow

interface ActionsImageComponent : ComposeDialogComponent {

    val model: StateFlow<ActionsImageStore.State>

    fun addFile(platformFile: PlatformFile)

    fun addFiles(platformFiles: List<PlatformFile>)

    fun deleteFile()

    fun navigateToKandinsky()

    sealed interface Output {

        data object Dismiss : Output

        data object KandinskyScreen : Output
    }
}
