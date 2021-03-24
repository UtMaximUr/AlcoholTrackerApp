package com.utmaximur.alcoholtracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.utmaximur.alcoholtracker.data.model.Drink

@Dao
interface DrinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDrink(drink: Drink)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateDrink(drink: Drink)

    @Query("SELECT * FROM drink_database")
    fun getDrinks(): LiveData<MutableList<Drink>>

    @Delete
    fun deleteDrink(drink: Drink)
}