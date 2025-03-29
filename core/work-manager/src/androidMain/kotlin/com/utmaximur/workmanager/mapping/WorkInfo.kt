@file:OptIn(ExperimentalUuidApi::class)

package com.utmaximur.workmanager.mapping

import androidx.work.WorkInfo
import com.utmaximur.workmanager.models.WorkerInfo
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.toKotlinUuid

internal fun WorkInfo.State.asLorraineInfoState() = when (this) {
    WorkInfo.State.ENQUEUED -> WorkerInfo.State.ENQUEUED
    WorkInfo.State.RUNNING -> WorkerInfo.State.RUNNING
    WorkInfo.State.SUCCEEDED -> WorkerInfo.State.SUCCEEDED
    WorkInfo.State.FAILED -> WorkerInfo.State.FAILED
    WorkInfo.State.BLOCKED -> WorkerInfo.State.BLOCKED
    WorkInfo.State.CANCELLED -> WorkerInfo.State.CANCELLED
}

internal fun WorkInfo.asLorraineInfo() = WorkerInfo(
    uuid = id.toKotlinUuid(),
    identifier = "",
    state = when (state) {
        WorkInfo.State.ENQUEUED -> WorkerInfo.State.ENQUEUED
        WorkInfo.State.RUNNING -> WorkerInfo.State.RUNNING
        WorkInfo.State.SUCCEEDED -> WorkerInfo.State.SUCCEEDED
        WorkInfo.State.FAILED -> WorkerInfo.State.FAILED
        WorkInfo.State.BLOCKED -> WorkerInfo.State.BLOCKED
        WorkInfo.State.CANCELLED -> WorkerInfo.State.CANCELLED
    },
    outputData = outputData.asLorraineData(),
    inputData = null,
    tags = tags
)