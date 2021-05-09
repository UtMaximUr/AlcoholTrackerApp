package com.utmaximur.alcoholtracker.repository

import com.utmaximur.alcoholtracker.data.assets.AssetsModule
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.model.Icon

class AssetsRepository(private var assetsModule: AssetsModule) {

    fun getIcons(): List<Icon> {
        return assetsModule.getIconsList()
    }

    fun getDrinks(): List<Drink> {
        return assetsModule.getDrinkList()
    }
}