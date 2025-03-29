package com.utmaximur.workmanager.work

/**
 * Represents a worker, which can perform some work.
 * Abstraction, which will be work inside real native work manager.
 */
interface Worker {
    val identifier: String get() = this::class.simpleName ?: error("can not be anonymous object")
    suspend fun doWork(inputData: WorkerData): WorkerResult

}