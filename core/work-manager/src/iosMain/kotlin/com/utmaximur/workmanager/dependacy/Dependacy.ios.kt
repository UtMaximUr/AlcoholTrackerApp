package com.utmaximur.workmanager.dependacy

import kotlinx.coroutines.CoroutineScope
import com.utmaximur.workmanager.CommonWorkManagerOperation
import com.utmaximur.workmanager.IOSCommonWorkManagerOperation
import com.utmaximur.workmanager.db.dao.WorkerDao

internal actual class CommonWorkManagerOperationFactory(
    private val dao: WorkerDao,
    private val scope: CoroutineScope
) : Factory<CommonWorkManagerOperation> {
    actual override fun create(): CommonWorkManagerOperation = IOSCommonWorkManagerOperation(dao, scope)
}