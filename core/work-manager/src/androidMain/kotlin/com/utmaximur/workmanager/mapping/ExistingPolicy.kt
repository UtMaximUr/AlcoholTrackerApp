package com.utmaximur.workmanager.mapping

import androidx.work.ExistingWorkPolicy
import com.utmaximur.workmanager.models.ExistingWorkerPolicy

internal fun ExistingWorkerPolicy.asWorkManagerExistingPolicy(): ExistingWorkPolicy {
    return when (this) {
        ExistingWorkerPolicy.APPEND -> ExistingWorkPolicy.APPEND
        ExistingWorkerPolicy.APPEND_OR_REPLACE -> ExistingWorkPolicy.APPEND_OR_REPLACE
        ExistingWorkerPolicy.REPLACE -> ExistingWorkPolicy.REPLACE
        ExistingWorkerPolicy.KEEP -> ExistingWorkPolicy.KEEP
    }
}