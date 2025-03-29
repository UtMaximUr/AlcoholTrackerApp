@file:OptIn(ExperimentalForeignApi::class)

package com.utmaximur.workmanager.db

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

internal fun getDatabaseBuilder(): RoomDatabase.Builder<WorkerWrapperDB> {
    val dbFile = "${fileDirectory()}/$WORKER_WRAPPER_DATABASE"
    return Room.databaseBuilder<WorkerWrapperDB>(name = dbFile)

}
@OptIn(ExperimentalForeignApi::class)
private fun fileDirectory(): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
