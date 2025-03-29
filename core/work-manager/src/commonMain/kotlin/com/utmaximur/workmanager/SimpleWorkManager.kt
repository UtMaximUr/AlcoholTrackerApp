@file:OptIn(ExperimentalUuidApi::class)

package com.utmaximur.workmanager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.utmaximur.workmanager.constraint.ConnectivityCheck
import com.utmaximur.workmanager.db.entity.WorkerEntity
import com.utmaximur.workmanager.db.entity.toInfo
import com.utmaximur.workmanager.dependacy.ApplicationContainer
import com.utmaximur.workmanager.dsl.WorkerDefinition
import com.utmaximur.workmanager.models.ExistingWorkerPolicy
import com.utmaximur.workmanager.models.MultipleOperation
import com.utmaximur.workmanager.models.OneTimeOperation
import com.utmaximur.workmanager.models.WorkerInfo
import com.utmaximur.workmanager.work.Worker
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Instance of [SimpleWorkManager], to enqueue lorraine's workers
 */
object SimpleWorkManager {

    internal lateinit var applicationContainer: ApplicationContainer

    internal val commonWorkManagerOperation by lazy { applicationContainer.commonWorkManagerOperation }
    private val dao by lazy { applicationContainer.database.workerDao() }

    private val logger by lazy { applicationContainer.defaultLogger }
    private var loggerEnable: Boolean = false

    //Todo will be remade, but practice
    internal val definitions = mutableMapOf<String, Worker>()

    internal val constraintChecks = listOf(ConnectivityCheck)

    internal fun initialize(definition: WorkerDefinition) {
        logMessage("Initializing SimpleWorkManager" + "with initial definition:${definition.definitions.size}")
        definitions.putAll(definition.definitions)
        loggerEnable = definition.loggerDefinition?.enable == true
    }

    /**
     * Enqueue a [OneTimeOperation]
     * existingWorkerPolicy by default equals to [ExistingWorkerPolicy.APPEND]
     * @param request, actual request
     */
    suspend fun enqueue(
        request: OneTimeOperation
    ) {
        logMessage("Enqueueing OneTimeOperation")
        commonWorkManagerOperation.enqueue(oneTimeOperation = request)
    }

    /**
     * Enqueue a [OneTimeOperation]
     *
     * @param queueId of the request
     * @param type to enqueue
     * @param request, actual request
     */
    suspend fun enqueue(
        queueId: String, type: ExistingWorkerPolicy, request: OneTimeOperation
    ) {
        logMessage("Enqueueing OneTimeOperation with queueId: $queueId, type: $type")
        commonWorkManagerOperation.enqueue(queueId = queueId, type = type, oneTimeOperation = request)
    }

    /**
     * Enqueue a [MultipleOperation] that contains multiple [OneTimeOperation]
     *
     * @param queueId for the queue
     * @param operation to enqueue
     */
    suspend fun enqueue(
        queueId: String, operation: MultipleOperation
    ) {
        logMessage("Enqueueing MultipleOperation with queueId: $queueId")
        commonWorkManagerOperation.enqueue(queueId = queueId, multipleOperation = operation)
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun cancelWorkById(uuid: Uuid) {
        logMessage("Cancelling work with id: $uuid")
        commonWorkManagerOperation.cancelWorkById(uuid)
    }

    suspend fun cancelUniqueWork(queueId: String) {
        logMessage("Cancelling unique work with queueId: $queueId")
        commonWorkManagerOperation.cancelUniqueWork(queueId)
    }

    suspend fun cancelAllWorkByTag(tag: String) {
        logMessage("Cancelling all work with tag: $tag")
        commonWorkManagerOperation.cancelAllWorkByTag(tag)
    }

    suspend fun cancelAllWork() {
        logMessage("Cancelling all work")
        commonWorkManagerOperation.cancelAllWork()
        dao.getWorkers().forEach { dao.delete(it) }
    }

    suspend fun pruneWork() {
        logMessage("Pruning work")
        commonWorkManagerOperation.pruneWork()
    }

    fun observeWorkInfo(): Flow<List<WorkerInfo>> {
        logMessage("Observing work info")
        return dao.getWorkersAsFlow().map { list -> list.map(WorkerEntity::toInfo) }
    }

    fun observeWorkInfoForTag(tag: String): Flow<WorkerInfo?> {
        logMessage("Observing work info")
        return dao.getWorkerForTagFlow(tag).map { it?.toInfo() }
    }

    private fun logMessage(message: String) {
        if (loggerEnable) {
            logger.log(message = message)
        }
    }
}

internal expect fun registerPlatform()