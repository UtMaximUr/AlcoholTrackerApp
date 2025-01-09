package com.utmaximur.confirmDialog.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.confirmDialog.store.ConfirmDialogStore.Intent
import com.utmaximur.confirmDialog.store.ConfirmDialogStore.Label
import com.utmaximur.confirmDialog.store.ConfirmDialogStore.State

interface ConfirmDialogStore : Store<Intent, State, Label> {

    data object State

    sealed interface Intent {

        data class Confirm(val id: Long) : Intent

    }

    sealed interface Label {

        data object CloseEvent : Label
    }
}