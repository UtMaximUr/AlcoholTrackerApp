package com.utmaximur.alcoholtracker.repository

import androidx.lifecycle.LiveData
import com.utmaximur.alcoholtracker.data.resources.IconRaw
import com.utmaximur.alcoholtracker.data.model.Icon

class IconRepository(private var iconRaw: IconRaw) {

    fun getIcons(): LiveData<List<Icon>> {
        return iconRaw.getIconsList()
    }
}