package com.utmaximur.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

abstract class Interactor<in P, R> {
    private val loadingState = MutableStateFlow(0)

    val inProgress: Flow<Boolean> = loadingState
        .map { it > 0 }
        .distinctUntilChanged()

    private fun addLoader() =
        loadingState.update { it.inc() }

    private fun removeLoader() =
        loadingState.update { it.dec() }


    suspend operator fun invoke(
        params: P,
        timeout: Duration = DefaultTimeout
    ): Result<R> = try {
        addLoader()
        runCatching {
            withTimeout(timeout) {
                doWork(params)
            }
        }
    } finally {
        removeLoader()
    }

    abstract fun doWork(params: P): R

    companion object {
        internal val DefaultTimeout = 1.minutes
    }
}

suspend operator fun <R> Interactor<Unit, R>.invoke(
    timeout: Duration = Interactor.DefaultTimeout
) = invoke(Unit, timeout)