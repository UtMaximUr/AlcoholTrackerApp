package com.utmaximur.workmanager.dependacy

import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import com.utmaximur.workmanager.AndroidCommonWorkManagerOperation
import com.utmaximur.workmanager.CommonWorkManagerOperation
import com.utmaximur.workmanager.db.dao.WorkerDao

internal actual class CommonWorkManagerOperationFactory(
    private val workManager: WorkManager,
    private val dao: WorkerDao,
    private val applicationScope: CoroutineScope,
) : Factory<CommonWorkManagerOperation> {
    actual override fun create(): CommonWorkManagerOperation =
        AndroidCommonWorkManagerOperation(workManager, dao, applicationScope)
}
