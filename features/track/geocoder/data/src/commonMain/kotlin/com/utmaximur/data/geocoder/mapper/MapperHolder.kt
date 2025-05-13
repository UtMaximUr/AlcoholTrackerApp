package com.utmaximur.data.geocoder.mapper

import org.koin.core.annotation.Factory

@Factory
internal class MapperHolder(
    val placeRemoteMapper: PlaceRemoteMapper,
    val placeDomainMapper: PlaceDomainMapper,
    val placeLocalMapper: PlaceLocalMapper,
)
