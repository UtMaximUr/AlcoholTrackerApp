package com.utmaximur.connection

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed interface NetworkStatus {
    data object Available : NetworkStatus

    data object Unavailable : NetworkStatus
}

inline fun <Result> Flow<NetworkStatus>.map(
    crossinline onUnavailable: suspend () -> Result,
    crossinline onAvailable: suspend () -> Result
): Flow<Result> = map { status ->
    when (status) {
        NetworkStatus.Unavailable -> onUnavailable()
        NetworkStatus.Available -> onAvailable()
    }
}