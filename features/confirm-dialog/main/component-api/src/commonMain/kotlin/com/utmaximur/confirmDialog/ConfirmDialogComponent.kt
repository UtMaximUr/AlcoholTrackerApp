package com.utmaximur.confirmDialog

import com.utmaximur.core.decompose.ComposeDialogComponent

interface ConfirmDialogComponent : ComposeDialogComponent {

    fun confirm()
}