package com.utmaximur.databaseRoom

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.annotation.Single
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import com.utmaximur.databaseRoom.base.AppRoomDataBase
import com.utmaximur.databaseRoom.base.dbFileName

@Single
internal fun provideIOSdDataBaseBuilder(): RoomDatabase.Builder<AppRoomDataBase> {
    val dbFile = "${fileDirectory()}/$dbFileName"
    return Room.databaseBuilder<AppRoomDataBase>(name = dbFile)
        .setDriver(BundledSQLiteDriver())
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