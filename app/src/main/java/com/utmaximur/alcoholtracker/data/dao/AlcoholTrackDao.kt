package com.utmaximur.alcoholtracker.data.dao

import androidx.lifecycle.LiveData
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

    @Query("SELECT * FROM track_database")
    fun getTracks(): LiveData<MutableList<AlcoholTrack>>

    @Query("SELECT * FROM track_database WHERE date == :date")
    fun getTrack(date: Long): LiveData<AlcoholTrack?>
}