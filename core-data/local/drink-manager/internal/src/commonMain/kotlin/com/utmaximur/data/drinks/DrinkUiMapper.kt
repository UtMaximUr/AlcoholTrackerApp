package com.utmaximur.data.drinks

import com.utmaximur.data.Mapper
import com.utmaximur.databaseRoom.drink.DbDrink
import com.utmaximur.domain.Drink
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
@Named(NAMED_DRINK_UI_MAPPER)
class DrinkUiMapper : Mapper<DbDrink, Drink> {
    override fun transform(from: DbDrink) = Drink(
        id = from.id,
        name = from.name,
        icon = from.icon,
        photo = from.photo,
        createdAt = from.createdAt,
    )
}
