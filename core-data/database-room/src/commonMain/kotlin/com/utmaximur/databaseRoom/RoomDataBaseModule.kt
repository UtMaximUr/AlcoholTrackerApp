package com.utmaximur.databaseRoom

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import com.utmaximur.databaseRoom.base.AppRoomDataBase
import com.utmaximur.databaseRoom.drink.DrinkDao
import com.utmaximur.databaseRoom.icon.IconDao
import com.utmaximur.databaseRoom.track.TrackDao

@Module
@ComponentScan
class RoomDataBaseModule {
    @Single
    fun provideDateBase(builder: RoomDatabase.Builder<AppRoomDataBase>): AppRoomDataBase =
        builder
            .fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .build()

    @Factory
    fun provideDrinkDao(dataBase: AppRoomDataBase): DrinkDao = dataBase.drinkDao()

    @Factory
    fun provideTrackDao(dataBase: AppRoomDataBase): TrackDao = dataBase.trackDao()

    @Factory
    fun provideIconDao(dataBase: AppRoomDataBase): IconDao = dataBase.iconDao()
}