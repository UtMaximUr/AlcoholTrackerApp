package com.utmaximur.databaseRoom.place


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.utmaximur.databaseRoom.base.BaseDao
import kotlinx.coroutines.flow.Flow


@Dao
interface PlaceDao : BaseDao<DbPlace> {

    @Query("SELECT * FROM DbPlace")
    fun getAll(): Flow<List<DbPlace>>

    @Query("SELECT * FROM DbPlace WHERE trackId=:id")
    fun getPlaceByTrackId(id: Long): Flow<DbPlace?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPlace(dbPlace: DbPlace)
}