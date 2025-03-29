@file:OptIn(ExperimentalUuidApi::class)

package com.utmaximur.workmanager.work

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import platform.Foundation.NSBlockOperation
import com.utmaximur.workmanager.SimpleWorkManager
import com.utmaximur.workmanager.SimpleWorkManager.constraintChecks
import com.utmaximur.workmanager.constraint.match
import com.utmaximur.workmanager.db.dao.WorkerDao
import com.utmaximur.workmanager.db.entity.toDomain
import com.utmaximur.workmanager.models.WorkerInfo
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal class IosWorker(
    private val workerUuid: Uuid,
    private val dao: WorkerDao
) : NSBlockOperation() {

    override fun isAsynchronous(): Boolean = true

    @OptIn(ExperimentalUuidApi::class)
    override fun main() {
        CoroutineScope(Dispatchers.IO).launch {
            val workerData =
                dao.getWorker(uuidString = workerUuid.toString()) ?: error("Worker not found")
            val identifier = requireNotNull(workerData.identifier) { "Identifier not found" }
            val worker = requireNotNull(SimpleWorkManager.definitions[identifier]) {
                "Worker definition not found"
            }

            // TODO Check dependencies
            if (!constraintChecks.match(workerData.constraints.toDomain())) {
                dao.update(workerData.copy(state = WorkerInfo.State.BLOCKED))
                return@launch
            }

            dao.update(workerData.copy(state = WorkerInfo.State.RUNNING))

            val result = worker.doWork(workerData.inputData ?: dataOf())
            val state = when (result) {
                is WorkerResult.Failure -> {
                    WorkerInfo.State.FAILED
                }

                is WorkerResult.Retry -> {
                    // TODO Re-enqueue
                    WorkerInfo.State.FAILED
                }

                is WorkerResult.Success -> {
                    // TODO Delete worker if not in operation
                    // TODO Delete all worker in operation, if all finish
                    WorkerInfo.State.SUCCEEDED
                }
            }

            dao.update(
                workerData.copy(
                    state = state,
                    outputData = result.outputData
                )
            )
        }
    }

    override fun cancel() {
        // TODO Update worker state in db
        runBlocking {
            val dao = dao
            val workerData = dao.getWorker(workerUuid.toString()) ?: error("Worker not found")

            dao.update(workerData.copy(state = WorkerInfo.State.CANCELLED))

            cancel()
        }
        super.cancel()
    }

}