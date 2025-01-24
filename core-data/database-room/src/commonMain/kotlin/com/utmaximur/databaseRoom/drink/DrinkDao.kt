package com.utmaximur.databaseRoom.drink


import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.utmaximur.databaseRoom.base.BaseDao

@Dao
interface DrinkDao : BaseDao<DbDrink> {

    @Query("SELECT * FROM DbDrink WHERE id=:id")
    fun getDrinkById(id: String): Flow<DbDrink>

    @Query("SELECT * FROM DbDrink")
    fun getAll(): Flow<List<DbDrink>>

    @Query("DELETE FROM DbDrink WHERE id=:id")
    fun deleteDrinkById(id: Long)
}