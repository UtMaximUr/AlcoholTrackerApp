package com.utmaximur.alcoholtracker.repository

import androidx.lifecycle.LiveData
import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.dao.DrinkDao
import com.utmaximur.alcoholtracker.data.dao.IconDao
import com.utmaximur.alcoholtracker.data.model.Icon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IconRepository(alcoholTrackDatabase: AlcoholTrackDatabase) {

    private var iconDao: IconDao = alcoholTrackDatabase.getIconDao()

    fun getIcons(): LiveData<MutableList<Icon>> {
        return iconDao.getIcons()
    }

}