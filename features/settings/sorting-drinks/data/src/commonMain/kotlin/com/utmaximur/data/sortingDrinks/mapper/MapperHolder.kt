package com.utmaximur.data.sortingDrinks.mapper

import org.koin.core.annotation.Factory

@Factory
internal class MapperHolder(
    val sortingDrinkDomainMapper: SortingDrinkDomainMapper,
    val sortingDrinkLocalMapper: SortingDrinkLocalMapper
)