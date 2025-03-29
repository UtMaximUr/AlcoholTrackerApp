package com.utmaximur.workmanager.work

import kotlin.coroutines.cancellation.CancellationException

sealed interface WorkerResult {
    val outputData: WorkerData?

    data class Success(
        override val outputData: WorkerData?
    ) : WorkerResult

    data class Failure(
        override val outputData: WorkerData?,
        val throwable: Throwable? = null
    ) : WorkerResult

    data class Retry(
        override val outputData: WorkerData?
    ) : WorkerResult

    companion object {

        fun success(outputData: WorkerData? = null): Success {
            return Success(outputData)
        }

        fun failure(outputData: WorkerData? = null, throwable: Throwable? = null): Failure {
            return Failure(outputData, throwable)
        }

        fun retry(outputData: WorkerData? = null): Retry {
            return Retry(outputData)
        }

    }

}

inline fun Worker.runCatchingWorker(block: () -> Unit): WorkerResult =
    runCatching {
        block()
    }
        .fold(
            onSuccess = {
                WorkerResult.success()
            },
            onFailure = {
                it.printStackTrace()
                if (it is CancellationException)
                    throw it
                else
                    WorkerResult.failure(throwable = it)
            }
        )

inline fun Worker.runCatchingWorkerWithData(block: () -> WorkerResult): WorkerResult =
    runCatching {
        block()
    }
        .fold(
            onSuccess = { result ->
                WorkerResult.success(result.outputData)
            },
            onFailure = {
                it.printStackTrace()
                if (it is CancellationException)
                    throw it
                else
                WorkerResult.failure(throwable = it)
            }
        )