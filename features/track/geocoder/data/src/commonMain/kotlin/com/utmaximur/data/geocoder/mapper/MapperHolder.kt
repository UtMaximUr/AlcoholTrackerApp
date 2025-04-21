package com.utmaximur.data.geocoder.mapper

import com.utmaximur.data.places.NAMED_PLACE_DOMAIN_MAPPER
import com.utmaximur.data.places.PlaceDomainMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
internal class MapperHolder(
    val placeRemoteMapper: PlaceRemoteMapper,
    @Named(NAMED_PLACE_DOMAIN_MAPPER)
    val placeDomainMapper: PlaceDomainMapper,
)
