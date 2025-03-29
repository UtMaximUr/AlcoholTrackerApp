package com.utmaximur.workmanager.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.utmaximur.workmanager.db.converter.DataConverter
import com.utmaximur.workmanager.db.converter.StringSetConverter
import com.utmaximur.workmanager.db.converter.UuidConverter
import com.utmaximur.workmanager.db.dao.WorkerDao
import com.utmaximur.workmanager.db.entity.WorkerEntity

internal const val WORKER_WRAPPER_DATABASE = "worker_wrapper.db"

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect object DbConstructor : RoomDatabaseConstructor<WorkerWrapperDB>{
    override fun initialize(): WorkerWrapperDB
}

@Database(
    version = 1,
    entities = [WorkerEntity::class]
)
@TypeConverters(
    StringSetConverter::class,
    DataConverter::class,
    UuidConverter::class
)

@ConstructedBy(DbConstructor::class)
internal abstract class WorkerWrapperDB : RoomDatabase() {
    internal abstract fun workerDao(): WorkerDao
}