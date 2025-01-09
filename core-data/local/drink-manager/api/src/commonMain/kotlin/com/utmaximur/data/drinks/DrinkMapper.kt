package com.utmaximur.data.drinks

import com.utmaximur.data.Mapper
import com.utmaximur.domain.models.Drink
import com.utmaximur.databaseRoom.drink.DbDrink

typealias DrinkLocalMapper = Mapper<Drink, DbDrink>
typealias DrinkUiMapper = Mapper<DbDrink, Drink>

const val NAMED_DRINK_LOCAL_MAPPER = "NAMED_DRINK_LOCAL_MAPPER"
const val NAMED_DRINK_UI_MAPPER = "NAMED_DRINK_UI_MAPPER"