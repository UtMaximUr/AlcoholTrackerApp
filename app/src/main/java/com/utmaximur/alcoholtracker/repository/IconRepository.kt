package com.utmaximur.alcoholtracker.repository

import com.utmaximur.alcoholtracker.data.assets.AssetsModule
import com.utmaximur.alcoholtracker.data.model.Icon

class IconRepository(private var assetsModule: AssetsModule) {

    fun getIcons(): List<Icon> {
        return assetsModule.getIconsList()
    }
}