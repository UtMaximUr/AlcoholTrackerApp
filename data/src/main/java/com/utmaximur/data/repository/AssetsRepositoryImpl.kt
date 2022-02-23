package com.utmaximur.data.repository

import com.utmaximur.data.assets.AssetsModule
import com.utmaximur.data.local_data_source.mapper.DrinkMapper
import com.utmaximur.data.assets.mapper.IconMapper
import com.utmaximur.domain.entity.Drink
import com.utmaximur.domain.entity.Icon
import com.utmaximur.domain.repository.AssetsRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AssetsRepositoryImpl @Inject constructor(
    private var assetsModule: AssetsModule,
    private var iconMapper: IconMapper,
    private var drinkMapper: DrinkMapper
): AssetsRepository {

    override fun getIcons(): List<Icon> {
        return assetsModule.getIconsList().map { iconMapper.map(it) }
    }

    fun getDrinks(): List<Drink> {
        return assetsModule.getDrinkList().map { drinkMapper.map(it) }
    }
}