package com.utmaximur.data.sortingDrinks.mapper

import com.utmaximur.data.Mapper
import com.utmaximur.databaseRoom.drink.DbDrink
import com.utmaximur.domain.sortingDrinks.SortingDrink
import org.koin.core.annotation.Factory

@Factory
internal class SortingDrinkDomainMapper : Mapper<DbDrink, SortingDrink> {
    override fun transform(from: DbDrink) = SortingDrink(
        id = from.id,
        name = from.name,
        position = from.position ?: 0,
        imageUrl = from.photo,
    )
}