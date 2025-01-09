package com.utmaximur.actions.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.actions.store.ActionsImageStore.Intent
import com.utmaximur.actions.store.ActionsImageStore.State
import com.utmaximur.actions.store.ActionsImageStore.Label
import com.utmaximur.media.PlatformFile

interface ActionsImageStore : Store<Intent, State, Label> {

    data object State

    sealed interface Intent {

        data class SelectedFile(val platformFile: PlatformFile) : Intent

        data class SelectedFiles(val platformFiles: List<PlatformFile>) : Intent

        data object DeleteFile : Intent
    }

    sealed interface Label {

        data object CloseEvent : Label

        data object DeleteFile : Label
    }
}