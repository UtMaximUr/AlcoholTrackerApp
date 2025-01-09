package com.utmaximur.databaseRoom.icon


import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.utmaximur.databaseRoom.base.BaseDao


@Dao
interface IconDao : BaseDao<DbIcon> {

    @Query("SELECT * FROM DbIcon")
    fun getAll(): Flow<List<DbIcon>>
}