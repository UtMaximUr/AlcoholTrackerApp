package com.utmaximur.workmanager.models

import kotlin.time.Duration

/**
 * Only on Android
 */
data class BackoffLorrainePolicy internal constructor(
    val duration: Duration,
    val policy: Policy
) {

    enum class Policy {
        EXPONENTIAL,
        LINEAR
    }

}