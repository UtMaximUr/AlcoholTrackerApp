package com.utmaximur.actions.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.utmaximur.actions.store.ActionsImageStore.Intent
import com.utmaximur.actions.store.ActionsImageStore.State
import com.utmaximur.actions.store.ActionsImageStore.Label
import com.utmaximur.app.base.app.ApplicationInfo
import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.actions.PathFileProviderData
import com.utmaximur.media.PlatformFile
import kotlinx.coroutines.launch

internal sealed interface Message {
    data class UpdateGenerateImageState(val isImageGenerationAvailable: Boolean) : Message
}

internal class ActionsImageExecutor(
    private val providerData: PathFileProviderData,
    private val applicationInfo: ApplicationInfo
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {

    override fun executeAction(action: Unit) {
        val isImageGenerationAvailable = applicationInfo.flavor.isAiAvailable()
        dispatch(Message.UpdateGenerateImageState(isImageGenerationAvailable))
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.SelectedFile -> handleFile(intent.platformFile)
            is Intent.SelectedFiles -> handleFiles(intent.platformFiles)
            Intent.DeleteFile -> handleDelete()
        }
    }

    private fun handleFile(file: PlatformFile) = scope.launch {
        providerData.sendData(file.uriString)
        close()
    }

    private fun handleFiles(files: List<PlatformFile>) = scope.launch {
        if (files.isEmpty()) {
            close()
            return@launch
        }
        providerData.sendData(files.first().uriString)
        close()
    }

    private fun handleDelete() = scope.launch {
        providerData.sendData(EMPTY_STRING)
        publish(Label.DeleteFile)
    }

    private fun close() = publish(Label.CloseEvent)
}