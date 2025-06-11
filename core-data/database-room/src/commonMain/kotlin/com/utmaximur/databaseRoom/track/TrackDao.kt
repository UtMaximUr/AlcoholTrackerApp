package com.utmaximur.databaseRoom.track


import androidx.room.Dao
import androidx.room.Query
import com.utmaximur.databaseRoom.base.BaseDao
import kotlinx.coroutines.flow.Flow


@Dao
interface TrackDao : BaseDao<DbTrack> {

    @Query("SELECT * FROM DbTrack WHERE id=:id")
    suspend fun getTrackById(id: Long): DbTrack

    @Query("SELECT * FROM DbTrack WHERE id IN (:ids)")
    fun getTracksByIds(ids: List<Long>): Flow<List<DbTrack>>

    @Query("SELECT * FROM DbTrack")
    fun getAll(): Flow<List<DbTrack>>

    @Query("SELECT * FROM DbTrack WHERE date BETWEEN :startDate AND :endDate")
    fun getTrackByMonth(
        startDate: Long,
        endDate: Long
    ): Flow<List<DbTrack>>

    @Query("DELETE FROM DbTrack WHERE id=:id")
    suspend fun deleteTrackById(id: Long)
}