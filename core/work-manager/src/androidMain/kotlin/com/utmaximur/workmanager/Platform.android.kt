@file:OptIn(ExperimentalUuidApi::class)

package com.utmaximur.workmanager

import android.annotation.SuppressLint
import android.os.Build
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkQuery
import androidx.work.await
import androidx.work.workDataOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import com.utmaximur.workmanager.db.dao.WorkerDao
import com.utmaximur.workmanager.db.entity.WorkerEntity
import com.utmaximur.workmanager.db.entity.toEntity
import com.utmaximur.workmanager.mapping.asLorraineData
import com.utmaximur.workmanager.mapping.asLorraineInfoState
import com.utmaximur.workmanager.mapping.asWorkManagerConstraints
import com.utmaximur.workmanager.mapping.asWorkManagerData
import com.utmaximur.workmanager.mapping.asWorkManagerExistingPolicy
import com.utmaximur.workmanager.mapping.asWorkManagerPolicy
import com.utmaximur.workmanager.models.ExistingWorkerPolicy
import com.utmaximur.workmanager.models.MultipleOperation
import com.utmaximur.workmanager.models.OneTimeOperation
import com.utmaximur.workmanager.models.WorkerInfo
import com.utmaximur.workmanager.work.RealWorker
import kotlin.time.toJavaDuration
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

internal class AndroidCommonWorkManagerOperation(
    private val workManager: WorkManager,
    private val dao: WorkerDao,
    applicationScope: CoroutineScope
) : CommonWorkManagerOperation {

    init {
        applicationScope.launch {
            initialized()
        }
    }

    private suspend fun initialized() =
        coroutineScope {
            launch {

                workManager.getWorkInfosFlow(WorkQuery.fromStates(WorkInfo.State.entries))
                    .collect { infos ->
                        infos.forEach { info ->
                            val uuid = info.id.toKotlinUuid()
                            val worker = dao.getWorker(uuidString = uuid.toHexString())
                                ?: return@forEach
                            dao.upsert(
                                worker.copy(
                                    outputData = info.outputData.asLorraineData(),
                                    state = info.state.asLorraineInfoState()
                                )
                            )
                        }
                    }
            }
        }

    override suspend fun enqueue(oneTimeOperation: OneTimeOperation) {
        val workManagerWorker = oneTimeOperation.toWorkManagerWorker()
        saveWorkerEntity(workManagerWorker.id.toKotlinUuid().toString(), Uuid.random().toString(), oneTimeOperation)
        workManager.enqueue(workManagerWorker)
    }

    override suspend fun enqueue(
        queueId: String,
        type: ExistingWorkerPolicy,
        oneTimeOperation: OneTimeOperation
    ) {
        val workManagerWorker = oneTimeOperation.toWorkManagerWorker()

        saveWorkerEntity(
            id = workManagerWorker.id.toKotlinUuid().toString(),
            queueId = queueId,
            operation = oneTimeOperation
        )

        workManager.enqueueUniqueWork(
            /* uniqueWorkName = */ queueId,
            /* existingWorkPolicy = */ type.asWorkManagerExistingPolicy(),
            /* work = */ workManagerWorker
        )
    }

    @SuppressLint("EnqueueWork")
    override suspend fun enqueue(
        queueId: String,
        multipleOperation: MultipleOperation
    ) {
        //TODO will think about organization chain workers, it's work parallel now

        /*       val firstOperation = multipleOperation.operations
                   .first()
               val firstWorkManagerWorker = firstOperation.request
                   .toWorkManagerWorker()
               var workOperation = workManager.beginUniqueWork(
                   *//* uniqueWorkName = *//* queueId,
            *//* existingWorkPolicy = *//* multipleOperation.existingPolicy.asWorkManagerExistingPolicy(),
            *//* work = *//* firstWorkManagerWorker
        )

        workOperation = multipleOperation.operations
            .drop(1)
            .fold(workOperation) { currentWorkOperation, operation ->
                val workManagerWorker = operation.request
                    .toWorkManagerWorker()

                dao.insert(
                    WorkerEntity(
                        uuid = workManagerWorker.id.toKotlinUuid().toString(),
                        queueId = queueId,
                        identifier = operation.request.identifier,
                        state = WorkerInfo.State.ENQUEUED,
                        tags = operation.request.tags,
                        inputData = operation.request.inputData,
                        outputData = null,
                        workerDependencies = emptySet(),
                        constraints = operation.request.commonConstraints.toEntity()
                    )
                )

                currentWorkOperation.then(workManagerWorker)
            }

        dao.insert(
            WorkerEntity(
                uuid = firstWorkManagerWorker.id.toKotlinUuid().toString(),
                queueId = queueId,
                identifier = firstOperation.request.identifier,
                state = WorkerInfo.State.ENQUEUED,
                tags = firstOperation.request.tags,
                inputData = firstOperation.request.inputData,
                outputData = null,
                workerDependencies = emptySet(),
                constraints = firstOperation.request.commonConstraints.toEntity()
            )
        )

        workOperation.enqueue()*/

        val multipleOperations = multipleOperation.operations.map { operation ->
            val workManagerWorker = operation.request.toWorkManagerWorker()

            dao.insert(
                WorkerEntity(
                    uuid = workManagerWorker.id.toKotlinUuid().toString(),
                    queueId = queueId,
                    identifier = operation.request.identifier,
                    state = WorkerInfo.State.ENQUEUED,
                    tags = operation.request.tags,
                    inputData = operation.request.inputData,
                    outputData = null,
                    workerDependencies = emptySet(),
                    constraints = operation.request.commonConstraints.toEntity()
                )
            )

            workManagerWorker
        }
        workManager.enqueue(multipleOperations)
    }

    override suspend fun cancelWorkById(uuid: Uuid) {
        workManager.cancelWorkById(uuid.toJavaUuid()).await()
    }

    override suspend fun cancelUniqueWork(queueId: String) {
        workManager.cancelUniqueWork(queueId).await()
    }

    override suspend fun cancelAllWorkByTag(tag: String) {
        workManager.cancelAllWorkByTag(tag).await()
    }

    override suspend fun cancelAllWork() {
        workManager.cancelAllWork().await()
    }

    override suspend fun pruneWork() {
        workManager.pruneWork().await()
    }

    private fun OneTimeOperation.toWorkManagerWorker(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<RealWorker>()
            .setInputData(inputData?.asWorkManagerData() ?: workDataOf())
            .setConstraints(commonConstraints.asWorkManagerConstraints())
            .apply {
                if (backOffPolicy != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        setBackoffCriteria(
                            backOffPolicy.policy.asWorkManagerPolicy(),
                            backOffPolicy.duration.toJavaDuration()
                        )
                    }
                }
            }
            .build()
    }

    private suspend fun saveWorkerEntity(id: String, queueId: String, operation: OneTimeOperation) {
        dao.insert(
            WorkerEntity(
                uuid = id,
                queueId = queueId,
                identifier = operation.identifier,
                state = WorkerInfo.State.ENQUEUED,
                tags = operation.tags,
                inputData = operation.inputData,
                outputData = null,
                workerDependencies = emptySet(),
                constraints = operation.commonConstraints.toEntity()
            )
        )
    }
}

internal actual fun registerPlatform() {
    // Initialisation inside android Initializer
}