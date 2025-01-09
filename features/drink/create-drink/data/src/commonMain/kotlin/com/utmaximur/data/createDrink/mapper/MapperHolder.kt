package com.utmaximur.data.createDrink.mapper

import com.utmaximur.data.drinks.DrinkLocalMapper
import com.utmaximur.data.drinks.NAMED_DRINK_LOCAL_MAPPER
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
internal class MapperHolder(
    @Named(NAMED_DRINK_LOCAL_MAPPER)
    val drinkLocalMapper: DrinkLocalMapper,
    val iconLocalMapper: IconLocalMapper
)