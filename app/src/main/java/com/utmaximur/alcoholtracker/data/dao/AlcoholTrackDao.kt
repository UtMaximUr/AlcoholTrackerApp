package com.utmaximur.alcoholtracker.data.dao

import androidx.room.*
import com.utmaximur.alcoholtracker.data.dbo.TrackDBO

@Dao
interface AlcoholTrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(trackDBO: TrackDBO)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTrack(trackDBO: TrackDBO)

    @Delete
    suspend fun deleteTrack(trackDBO: TrackDBO)

    @Query("SELECT * FROM track_database WHERE date == :date")
    suspend fun getTrack(date: Long): TrackDBO

    @Query("SELECT * FROM track_database WHERE id=:id")
    suspend fun getTrackById(id: String): TrackDBO

    @Query("SELECT * FROM track_database")
    suspend fun getTracks(): List<TrackDBO>

}