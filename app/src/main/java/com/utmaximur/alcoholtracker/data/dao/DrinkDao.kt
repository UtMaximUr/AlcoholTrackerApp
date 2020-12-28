package com.utmaximur.alcoholtracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.utmaximur.alcoholtracker.data.model.Drink

@Dao
interface DrinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDrink(drink: Drink)

    @Query("SELECT * FROM drink_database")
    fun getDrinks(): LiveData<MutableList<Drink>>
}