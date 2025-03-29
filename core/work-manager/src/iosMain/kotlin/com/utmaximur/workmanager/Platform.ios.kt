@file:OptIn(ExperimentalUuidApi::class)

package com.utmaximur.workmanager

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import platform.Foundation.NSOperation
import platform.Foundation.NSOperationQueue
import platform.darwin.NSInteger
import com.utmaximur.workmanager.constraint.match
import com.utmaximur.workmanager.db.dao.WorkerDao
import com.utmaximur.workmanager.db.entity.WorkerEntity
import com.utmaximur.workmanager.db.entity.createWorkerEntity
import com.utmaximur.workmanager.db.entity.toDomain
import com.utmaximur.workmanager.db.getDatabaseBuilder
import com.utmaximur.workmanager.dependacy.ApplicationContainer
import com.utmaximur.workmanager.dependacy.CommonWorkManagerOperationFactory
import com.utmaximur.workmanager.dependacy.DatabaseFactory
import com.utmaximur.workmanager.models.ExistingWorkerPolicy
import com.utmaximur.workmanager.models.MultipleOperation
import com.utmaximur.workmanager.models.OneTimeOperation
import com.utmaximur.workmanager.models.WorkerInfo
import com.utmaximur.workmanager.work.IosWorker
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal class IOSCommonWorkManagerOperation(
    private val dao: WorkerDao,
    private val scope: CoroutineScope
) : CommonWorkManagerOperation {

    private val queues: MutableMap<String, NSOperationQueue> = mutableMapOf()

    init {
        scope.launch {
            initialized()
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private suspend fun initialized() {
        dao.getWorkers()
            .groupBy(WorkerEntity::queueId)
            .forEach { (queueId, workers) ->
                val nsOperation = NSOperationQueue()
                var previous: NSOperation? = null

                workers.map { IosWorker(workerUuid = Uuid.parse(it.uuid), dao) }
                    .forEach { worker ->
                        previous?.let { previous -> worker.addDependency(previous) }
                        previous = worker
                        nsOperation.addOperation(worker)
                    }

                queues[queueId] = nsOperation
            }

        constraintChanged()
    }


    override suspend fun enqueue(oneTimeOperation: OneTimeOperation) {
        enqueue(
            queueId = Uuid.random().toString(),
            type = ExistingWorkerPolicy.APPEND,
            oneTimeOperation = oneTimeOperation
        )
    }

    override suspend fun enqueue(
        queueId: String,
        type: ExistingWorkerPolicy,
        oneTimeOperation: OneTimeOperation
    ) {
        val queue = queues.getOrElse(queueId) { createQueue(queueId) }
        val uuid = Uuid.random()
        val worker = createWorkerEntity(
            uuid = uuid,
            queueId = queueId,
            request = oneTimeOperation
        )

        dao.insert(worker)

        queue.addOperation(IosWorker(uuid, dao))
        queues[worker.queueId] = queue

        queue.suspended = !SimpleWorkManager.constraintChecks
            .match(worker.constraints.toDomain())


    }

    // TODO Add dependency between workers, now its parallel work
    override suspend fun enqueue(
        queueId: String,
        operation: MultipleOperation
    ) {
        requireNotNull(operation.operations.firstOrNull()) {
            "Operations should not be empty"
        }

        val queue = queues.getOrElse(queueId) {
            createQueue(
                queueId,
                maxConcurrentOperationCount = operation.operations.size
            )
        }
        var previous: NSOperation? = null

        val workers = operation.operations
            .map { operation ->
                val uuid = Uuid.random()

                createWorkerEntity(
                    uuid = uuid,
                    queueId = queueId,
                    request = operation.request
                )
            }

        workers.map { IosWorker(Uuid.parse(it.uuid), dao) }
            .forEach { worker ->
                previous?.let { previous -> worker.addDependency(previous) }
                previous = worker
                queue.addOperation(worker)
            }

        dao.insert(workers)

        queue.suspended = !SimpleWorkManager.constraintChecks
            .match(workers.first().constraints.toDomain())
    }


    private fun suspend(uniqueId: String, suspended: Boolean) {
        val queue = queues[uniqueId] ?: return

        queue.suspended = suspended
    }

    internal fun constraintChanged() {
        scope.launch {
            val workers = dao.getWorkers()

            workers.filter {
                when (it.state) {
                    WorkerInfo.State.BLOCKED,
                    WorkerInfo.State.ENQUEUED -> true

                    else -> false
                }
            }
                .forEach { worker ->
                    if (!worker.workerDependencies.all { id ->
                            workers.find { it.uuid == id }?.let {
                                it.state == WorkerInfo.State.SUCCEEDED
                            } != false
                        }) {
                        return@forEach
                    }

                    if (SimpleWorkManager.constraintChecks
                            .match(worker.constraints.toDomain())
                    ) {
                        suspend(worker.queueId, false)
                    }
                }
        }
    }

    override suspend fun cancelWorkById(uuid: Uuid) {
        val worker = dao.getWorker(uuidString = uuid.toString()) ?: error("Worker $uuid not found")
        queues.getOrElse(worker.queueId) {
            error("$uuid not found from $queues")
        }.cancelAllOperations()
    }

    override suspend fun cancelUniqueWork(queueId: String) {
        queues.getOrElse(queueId) {
            error("$queueId not found from $queues")
        }.cancelAllOperations()
    }

    override suspend fun cancelAllWorkByTag(tag: String) {
        val queueId = dao.getWorkerForTag(tag)?.queueId ?: error("this $tag not found from dao")
        queues.getOrElse(queueId) {
            error("$queueId not found from $queues")
        }.cancelAllOperations()
    }

    override suspend fun cancelAllWork() {
        queues.forEach { it.value.cancelAllOperations() }
        queues.clear()
    }

    override suspend fun pruneWork() {
        val completedWorkers = dao.getWorkersSucceeded()
        completedWorkers.forEach { worker ->
            queues.getOrElse(worker.queueId) {
                error("$worker with uuid ${worker.queueId} not found from $queues")
            }.cancelAllOperations()
        }
        dao.deleteWorkersSucceeded()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun createQueue(
        uniqueId: String,
        maxConcurrentOperationCount: Int = 1
    ): NSOperationQueue {
        return NSOperationQueue().apply {
            setName(uniqueId)
            setMaxConcurrentOperationCount(maxConcurrentOperationCount.convert<NSInteger>())
            setSuspended(true)
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
internal actual fun registerPlatform() {
    val database = DatabaseFactory(databaseBuilder = getDatabaseBuilder()).create()

    val dependenciesContainer = ApplicationContainer(
        database = database,
        commonWorkManagerOperationFactory = CommonWorkManagerOperationFactory(
            dao = database.workerDao(),
            scope = GlobalScope
        )
    )

    SimpleWorkManager.applicationContainer = dependenciesContainer
}
