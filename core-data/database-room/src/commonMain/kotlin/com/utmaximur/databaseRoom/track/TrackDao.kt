package com.utmaximur.databaseRoom.track


import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.utmaximur.databaseRoom.base.BaseDao


@Dao
interface TrackDao : BaseDao<DbTrack> {

    @Query("SELECT * FROM DbTrack WHERE id=:id")
    fun getTrackById(id: Long): Flow<DbTrack>

    @Query("SELECT * FROM DbTrack")
    fun getAll(): Flow<List<DbTrack>>

    @Query("SELECT * FROM DbTrack")
    fun getTracksByStartDate(): Flow<List<DbTrack>>

    @Query("DELETE FROM DbTrack WHERE id=:id")
    fun deleteTrackById(id: Long)
}