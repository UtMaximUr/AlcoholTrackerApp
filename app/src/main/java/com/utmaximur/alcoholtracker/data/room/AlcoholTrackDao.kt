package com.utmaximur.alcoholtracker.data.room

import androidx.room.*
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack

@Dao
interface AlcoholTrackDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrack(track: AlcoholTrack)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTrack(track: AlcoholTrack)

    @Delete
    fun deleteTrack(track: AlcoholTrack)

//    @Query("SELECT * FROM track_database WHERE date == :date")
//    @Query("SELECT * FROM track_database")
//    fun getAlcoholTrackByMonth(date: Long): MutableList<AlcoholTrack>
//    fun getAlcoholTrackByMonth(): MutableList<AlcoholTrack>

    @Query("SELECT * FROM track_database")
    fun getTracks(): MutableList<AlcoholTrack>

    @Query("SELECT * FROM track_database WHERE date == :date")
    fun getTrack(date: Long): AlcoholTrack?
}