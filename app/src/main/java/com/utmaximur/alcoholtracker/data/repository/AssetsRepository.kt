package com.utmaximur.alcoholtracker.data.repository

import com.utmaximur.alcoholtracker.data.assets.AssetsModule
import com.utmaximur.alcoholtracker.data.dbo.DrinkDBO
import com.utmaximur.alcoholtracker.data.dbo.IconDBO

class AssetsRepository(private var assetsModule: AssetsModule) {

    fun getIcons(): List<IconDBO> {
        return assetsModule.getIconsList()
    }

    fun getDrinks(): List<DrinkDBO> {
        return assetsModule.getDrinkList()
    }
}