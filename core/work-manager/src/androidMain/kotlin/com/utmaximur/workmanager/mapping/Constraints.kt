package com.utmaximur.workmanager.mapping

import androidx.work.Constraints
import androidx.work.NetworkType

internal fun com.utmaximur.workmanager.dsl.CommonConstraints.asWorkManagerConstraints(): Constraints {
    return Constraints(
        requiredNetworkType = if (requireNetwork) {
            NetworkType.CONNECTED
        } else {
            NetworkType.NOT_REQUIRED
        }
    )
}