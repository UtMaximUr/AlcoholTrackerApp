package com.utmaximur.data.splash_screen.mapper

import com.utmaximur.data.Mapper
import com.utmaximur.data.splash_screen.network.DrinkRemote
import org.koin.core.annotation.Factory
import com.utmaximur.databaseRoom.drink.DbDrink

@Factory
internal class DrinkRemoteMapper : Mapper<DrinkRemote, DbDrink> {
    override fun transform(from: DrinkRemote) = DbDrink(
        id = from.id.toLong(),
        name = from.name,
        icon = from.icon,
        photo = from.photo
    )
}