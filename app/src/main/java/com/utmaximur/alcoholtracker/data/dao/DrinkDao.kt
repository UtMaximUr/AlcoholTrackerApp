package com.utmaximur.alcoholtracker.data.dao

import androidx.room.*
import com.utmaximur.alcoholtracker.data.dbo.DrinkDBO

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