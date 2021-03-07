package com.utmaximur.alcoholtracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.utmaximur.alcoholtracker.data.model.Icon

@Dao
interface IconDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addIcon(icon: Icon)

    @Query("SELECT * FROM icon_database")
    fun getIcons(): LiveData<MutableList<Icon>>
}