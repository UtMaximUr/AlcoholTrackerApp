package com.utmaximur.databaseRoom

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.annotation.Factory
import com.utmaximur.databaseRoom.base.AppRoomDataBase
import com.utmaximur.databaseRoom.base.dbFileName

@Factory
internal fun provideAndroidDataBaseBuilder(
    applicationContext: Context,
): RoomDatabase.Builder<AppRoomDataBase> {
    val dbFile = applicationContext.getDatabasePath(dbFileName)
    return Room.databaseBuilder<AppRoomDataBase>(applicationContext, dbFile.absolutePath)
        //.setDriver(AndroidSQLiteDriver())
}