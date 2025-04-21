package com.utmaximur.data.sortingDrinks.mapper

import com.utmaximur.data.Mapper
import com.utmaximur.databaseRoom.drink.DbDrinkPosition
import com.utmaximur.domain.sortingDrinks.SortingDrink
import org.koin.core.annotation.Factory

@Factory
internal class SortingDrinkLocalMapper : Mapper<SortingDrink, DbDrinkPosition> {
    override fun transform(from: SortingDrink) = DbDrinkPosition(
        id = from.id,
        position = from.position,
    )
}