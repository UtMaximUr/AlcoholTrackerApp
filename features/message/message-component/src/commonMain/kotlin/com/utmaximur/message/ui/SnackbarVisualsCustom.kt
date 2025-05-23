package com.utmaximur.message.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

data class SnackbarVisualsCustom(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = true,
    override val duration: SnackbarDuration,
    val type: SnackbarType = SnackbarType.DEFAULT,
    val onClickAction: () -> Unit = {}
) : SnackbarVisuals