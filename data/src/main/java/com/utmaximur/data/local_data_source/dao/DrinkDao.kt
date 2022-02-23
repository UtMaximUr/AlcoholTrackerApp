package com.utmaximur.data.local_data_source.dao

import androidx.room.*
import com.utmaximur.data.local_data_source.dbo.DrinkDBO

@Dao
interface DrinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDrink(drinkDBO: DrinkDBO)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDrink(drinkDBO: DrinkDBO)

    @Query("SELECT * FROM drink_database WHERE id=:id")
    suspend fun getDrinkById(id: String): DrinkDBO

    @Query("SELECT * FROM drink_database")
    suspend fun getDrinks(): List<DrinkDBO>

    @Delete
    suspend fun deleteDrink(drinkDBO: DrinkDBO)
}