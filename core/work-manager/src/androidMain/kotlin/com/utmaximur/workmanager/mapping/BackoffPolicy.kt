package com.utmaximur.workmanager.mapping

import androidx.work.BackoffPolicy
import com.utmaximur.workmanager.models.BackoffLorrainePolicy

internal fun BackoffPolicy.asLorraineBackoffPolicy() = when (this) {
    BackoffPolicy.EXPONENTIAL -> BackoffLorrainePolicy.Policy.EXPONENTIAL
    BackoffPolicy.LINEAR -> BackoffLorrainePolicy.Policy.LINEAR
}

internal fun BackoffLorrainePolicy.Policy.asWorkManagerPolicy() = when (this) {
    BackoffLorrainePolicy.Policy.EXPONENTIAL -> BackoffPolicy.EXPONENTIAL
    BackoffLorrainePolicy.Policy.LINEAR -> BackoffPolicy.LINEAR
}