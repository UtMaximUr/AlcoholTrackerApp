package com.utmaximur.alcoholtracker.repository

import com.utmaximur.alcoholtracker.data.resources.IconRaw
import com.utmaximur.alcoholtracker.data.model.Icon

class IconRepository(private var iconRaw: IconRaw) {

    fun getIcons(): List<Icon> {
        return iconRaw.getIconsList()
    }
}