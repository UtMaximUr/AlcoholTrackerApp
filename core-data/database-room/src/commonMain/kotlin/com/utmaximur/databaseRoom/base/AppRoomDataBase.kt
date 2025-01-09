package com.utmaximur.databaseRoom.base

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.utmaximur.databaseRoom.base.converters.DateConverter
import com.utmaximur.databaseRoom.base.converters.ListStringConverter
import com.utmaximur.databaseRoom.base.converters.LocalDateConverter
import com.utmaximur.databaseRoom.base.converters.LocalDateTimeConverter
import com.utmaximur.databaseRoom.drink.DbDrink
import com.utmaximur.databaseRoom.drink.DrinkDao
import com.utmaximur.databaseRoom.icon.DbIcon
import com.utmaximur.databaseRoom.icon.IconDao
import com.utmaximur.databaseRoom.track.DbTrack
import com.utmaximur.databaseRoom.track.TrackDao

internal const val dbFileName = "AlcoholTracker.db"

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppRoomDataBase> {
    override fun initialize(): AppRoomDataBase
}

@Database(
    entities = [
        DbDrink::class,
        DbTrack::class,
        DbIcon::class
    ],
    views = [

    ],
    version = 1
)
@TypeConverters(
    value = [
        DateConverter::class,
        LocalDateConverter::class,
        LocalDateTimeConverter::class,
        ListStringConverter::class
    ]
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppRoomDataBase : RoomDatabase() {

    abstract fun drinkDao(): DrinkDao
    abstract fun trackDao(): TrackDao
    abstract fun iconDao(): IconDao

}
