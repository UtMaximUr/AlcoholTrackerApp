package com.utmaximur.workmanager.dependacy

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.utmaximur.workmanager.CommonWorkManagerOperation
import com.utmaximur.workmanager.db.WorkerWrapperDB
import com.utmaximur.workmanager.logger.DefaultLogger
import com.utmaximur.workmanager.logger.Logger

internal fun interface Factory<out T> {
    fun create(): T
}

internal class DatabaseFactory(
    private val databaseBuilder: RoomDatabase.Builder<WorkerWrapperDB>
) : Factory<WorkerWrapperDB> {
    override fun create(): WorkerWrapperDB =
        databaseBuilder.fallbackToDestructiveMigration(dropAllTables = true)
            .setDriver(BundledSQLiteDriver())
            .build()
}

internal expect class CommonWorkManagerOperationFactory : Factory<CommonWorkManagerOperation>{
    override fun create(): CommonWorkManagerOperation
}

internal class ApplicationContainer(
    database: WorkerWrapperDB,
    commonWorkManagerOperationFactory: Factory<CommonWorkManagerOperation>
) {
    val database: WorkerWrapperDB by lazy { database }

    val commonWorkManagerOperation: CommonWorkManagerOperation by lazy {
        commonWorkManagerOperationFactory.create()
    }

    val defaultLogger: Logger by lazy { DefaultLogger }
}
