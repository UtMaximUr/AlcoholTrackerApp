package com.utmaximur.databaseRoom.drink


import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.utmaximur.databaseRoom.base.BaseDao

@Dao
interface DrinkDao : BaseDao<DbDrink> {

    @Query("SELECT * FROM DbDrink WHERE id=:id")
    fun getDrinkById(id: String): Flow<DbDrink>

    @Query("SELECT * FROM DbDrink ORDER by position ASC")
    fun getAll(): Flow<List<DbDrink>>

    @Query("DELETE FROM DbDrink WHERE id=:id")
    suspend fun deleteDrinkById(id: Long)

    @Update(entity = DbDrink::class)
    suspend fun updatePositions(positions: List<DbDrinkPosition>)

    @Query("SELECT (SELECT COUNT(*) FROM DbDrink) != 0")
    suspend fun isTableNotEmpty(): Boolean
}