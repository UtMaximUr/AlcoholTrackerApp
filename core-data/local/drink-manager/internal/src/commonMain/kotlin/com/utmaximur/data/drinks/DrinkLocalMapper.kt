package com.utmaximur.data.drinks

import com.utmaximur.data.Mapper
import com.utmaximur.domain.models.Drink
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import com.utmaximur.databaseRoom.drink.DbDrink

@Factory
@Named(NAMED_DRINK_LOCAL_MAPPER)
internal class DrinkLocalMapper : Mapper<Drink, DbDrink> {
    override fun transform(from: Drink) = DbDrink(
        id = from.id,
        name = from.name,
        icon = from.icon,
        photo = from.photo,
        createdAt = from.createdAt
    )
}