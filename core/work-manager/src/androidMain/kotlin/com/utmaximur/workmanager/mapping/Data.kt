package com.utmaximur.workmanager.mapping

import androidx.work.Data
import androidx.work.workDataOf
import com.utmaximur.workmanager.work.WorkerData

internal fun WorkerData.asWorkManagerData(): Data = workDataOf(
    *(map.toList().toTypedArray())
)

internal fun Data.asLorraineData() = WorkerData(keyValueMap)