package com.utmaximur.workmanager.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

internal fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<WorkerWrapperDB> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath(WORKER_WRAPPER_DATABASE)

    return Room.databaseBuilder<WorkerWrapperDB>(
        context = appContext,
        name = dbFile.absolutePath
    )
}