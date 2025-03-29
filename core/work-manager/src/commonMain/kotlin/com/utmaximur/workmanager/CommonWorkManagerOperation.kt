@file:OptIn(ExperimentalUuidApi::class)

package com.utmaximur.workmanager

import com.utmaximur.workmanager.models.ExistingWorkerPolicy
import com.utmaximur.workmanager.models.MultipleOperation
import com.utmaximur.workmanager.models.OneTimeOperation
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal interface CommonWorkManagerOperation {

    suspend fun enqueue(oneTimeOperation: OneTimeOperation)

    suspend fun enqueue(
        queueId: String,
        type: ExistingWorkerPolicy,
        oneTimeOperation: OneTimeOperation
    )

    suspend fun enqueue(
        queueId: String,
        multipleOperation: MultipleOperation
    )

    suspend fun cancelWorkById(uuid: Uuid)

    suspend fun cancelUniqueWork(queueId: String)

    suspend fun cancelAllWorkByTag(tag: String)

    suspend fun cancelAllWork()

    suspend fun pruneWork()

}