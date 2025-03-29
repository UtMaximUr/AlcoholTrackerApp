package com.utmaximur.workmanager.db.entity

import androidx.room.ColumnInfo
import com.utmaximur.workmanager.dsl.CommonConstraints

internal data class ConstraintEntity(

    @ColumnInfo(name = "require_network")
    val requireNetwork: Boolean

)

internal fun ConstraintEntity.toDomain() = CommonConstraints(
    requireNetwork = requireNetwork
)

internal fun CommonConstraints.toEntity() = ConstraintEntity(
    requireNetwork = requireNetwork
)