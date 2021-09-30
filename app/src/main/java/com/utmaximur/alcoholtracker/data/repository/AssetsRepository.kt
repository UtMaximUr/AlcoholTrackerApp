package com.utmaximur.alcoholtracker.data.repository

import com.utmaximur.alcoholtracker.data.assets.AssetsModule
import com.utmaximur.alcoholtracker.data.dbo.DrinkDBO
import com.utmaximur.alcoholtracker.data.dbo.IconDBO
import com.utmaximur.alcoholtracker.data.mapper.DrinkMapper
import com.utmaximur.alcoholtracker.data.mapper.IconMapper
import com.utmaximur.alcoholtracker.domain.entity.Drink
import com.utmaximur.alcoholtracker.domain.entity.Icon

class AssetsRepository(
    private var assetsModule: AssetsModule,
    private var iconMapper: IconMapper,
    private var drinkMapper: DrinkMapper
) {

    fun getIcons(): List<Icon> {
        return assetsModule.getIconsList().map { iconMapper.map(it) }
    }

    fun getDrinks(): List<Drink> {
        return assetsModule.getDrinkList().map { drinkMapper.map(it) }
    }
}