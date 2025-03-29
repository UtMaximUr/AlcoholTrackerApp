@file:OptIn(ExperimentalUuidApi::class)

package com.utmaximur.workmanager.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.utmaximur.workmanager.SimpleWorkManager
import com.utmaximur.workmanager.db.dao.WorkerDao
import com.utmaximur.workmanager.models.WorkerInfo
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.toKotlinUuid

internal class RealWorker(
    appContext: Context,
    params: WorkerParameters,
    private val dao: WorkerDao
) : CoroutineWorker(appContext, params) {

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun doWork(): Result {
        val uuid = id.toKotlinUuid()
        val worker = requireNotNull(dao.getWorker(uuidString = uuid.toString())) {
            "Worker not found"
        }

        val workerDefinition: Worker =
            requireNotNull(SimpleWorkManager.definitions[worker.identifier]) {
                "Worker definition not found for this identifier: ${worker.identifier}"
            }

        return runCatching {
            dao.update(worker.copy(state = WorkerInfo.State.RUNNING))
            workerDefinition.doWork(worker.inputData ?: dataOf())
        }
            .fold(
                onSuccess = { result ->
                    when (result) {
                        is WorkerResult.Failure -> {
                            result.throwable?.printStackTrace()
                            dao.update(worker.copy(state = WorkerInfo.State.FAILED))
                            Result.failure(Data.Builder().putString("error", result.throwable?.message).build())
                        }

                        is WorkerResult.Retry -> {
                            dao.update(worker.copy(state = WorkerInfo.State.ENQUEUED))
                            Result.retry()
                        }

                        is WorkerResult.Success -> {
                            dao.update(worker.copy(state = WorkerInfo.State.SUCCEEDED))
                            Result.success()
                        }
                    }
                },
                onFailure = {
                    it.printStackTrace()
                    dao.update(worker.copy(state = WorkerInfo.State.FAILED))
                    Result.failure()
                }
            )
    }

    internal class RealWorkerFactory(
        private val dao: WorkerDao
    ) : WorkerFactory() {
        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
        ): CoroutineWorker {
            return RealWorker(appContext, workerParameters, dao)
        }
    }
}
