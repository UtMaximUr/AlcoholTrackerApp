package com.utmaximur.alcoholtracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.utmaximur.alcoholtracker.data.conversion.Converters
import com.utmaximur.alcoholtracker.data.dbo.TrackDBO
import com.utmaximur.alcoholtracker.data.dbo.DrinkDBO
import com.utmaximur.alcoholtracker.data.dao.AlcoholTrackDao
import com.utmaximur.alcoholtracker.data.dao.DrinkDao

@Database(entities = [DrinkDBO::class, TrackDBO::class], version = 5)
@TypeConverters(Converters::class)
abstract class AlcoholTrackDatabase: RoomDatabase(){
    abstract fun getDrinkDao(): DrinkDao
    abstract fun getTrackDao(): AlcoholTrackDao
}