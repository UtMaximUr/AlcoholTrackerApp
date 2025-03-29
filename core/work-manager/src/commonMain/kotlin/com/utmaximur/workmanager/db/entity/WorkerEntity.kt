@file:OptIn(ExperimentalUuidApi::class)

package com.utmaximur.workmanager.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import com.utmaximur.workmanager.db.converter.DataConverter
import com.utmaximur.workmanager.db.converter.StringSetConverter
import com.utmaximur.workmanager.models.OneTimeOperation
import com.utmaximur.workmanager.models.WorkerInfo
import com.utmaximur.workmanager.work.WorkerData
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(
    primaryKeys = ["uuid"],
    tableName = WorkerEntity.TABLE_NAME
)
internal data class WorkerEntity(

    @ColumnInfo(name = "uuid")
//    @TypeConverters(UuidConverter::class) When Uuid is stable
    val uuid: String,

    @ColumnInfo(name = "queue_id")
    val queueId: String,

    @ColumnInfo(name = "identifier")
    val identifier: String,

    @ColumnInfo(name = "state")
    val state: WorkerInfo.State,

    @ColumnInfo(name = "tags")
    @TypeConverters(StringSetConverter::class)
    val tags: Set<String>,

    @ColumnInfo(name = "worker_dependencies")
    @TypeConverters(StringSetConverter::class)
    val workerDependencies: Set<String>,

    @ColumnInfo(name = "input_data")
    @TypeConverters(DataConverter::class)
    val inputData: WorkerData? = null,

    @ColumnInfo(name = "output_data")
    @TypeConverters(DataConverter::class)
    val outputData: WorkerData? = null,

    @Embedded(prefix = "constraints_")
    val constraints: ConstraintEntity

) {

    companion object {
        const val TABLE_NAME = "worker"
    }

}


internal fun createWorkerEntity(
    uuid: Uuid,
    queueId: String,
    request: OneTimeOperation,
    state: WorkerInfo.State = WorkerInfo.State.ENQUEUED
): WorkerEntity {
    return WorkerEntity(
        uuid = uuid.toString(),
        queueId = queueId,
        identifier = request.identifier,
        state = state,
        tags = request.tags,
        inputData = request.inputData,
        outputData = null,
        workerDependencies = emptySet(),
        constraints = request.commonConstraints.toEntity()
    )
}

@OptIn(ExperimentalUuidApi::class)
internal fun WorkerEntity.toInfo() = WorkerInfo(
    uuid = Uuid.parse(uuid),
    state = state,
    identifier = identifier,
    inputData = inputData,
    outputData = outputData,
    tags = tags
)