package com.utmaximur.alcoholtracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.utmaximur.alcoholtracker.data.conversion.Converters
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.dao.AlcoholTrackDao
import com.utmaximur.alcoholtracker.data.dao.DrinkDao
import com.utmaximur.alcoholtracker.data.dao.IconDao
import com.utmaximur.alcoholtracker.data.model.Icon

@Database(entities = [Drink::class, AlcoholTrack::class, Icon::class], version = 2)
@TypeConverters(Converters::class)
abstract class AlcoholTrackDatabase: RoomDatabase(){
    abstract fun getDrinkDao(): DrinkDao
    abstract fun getTrackDao(): AlcoholTrackDao
    abstract fun getIconDao(): IconDao
}