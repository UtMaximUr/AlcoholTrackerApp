package com.utmaximur.data.local_data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.utmaximur.data.local_data_source.conversion.Converters
import com.utmaximur.data.local_data_source.dao.AlcoholTrackDao
import com.utmaximur.data.local_data_source.dao.DrinkDao
import com.utmaximur.data.local_data_source.dbo.DrinkDBO
import com.utmaximur.data.local_data_source.dbo.TrackDBO

@Database(entities = [DrinkDBO::class, TrackDBO::class], version = 5)
@TypeConverters(Converters::class)
abstract class AlcoholTrackDatabase: RoomDatabase(){
    abstract fun getDrinkDao(): DrinkDao
    abstract fun getTrackDao(): AlcoholTrackDao
}