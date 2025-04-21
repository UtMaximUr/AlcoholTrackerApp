package com.utmaximur.data.createTrack

import com.utmaximur.data.drinks.DrinkDomainMapper
import com.utmaximur.data.drinks.NAMED_DRINK_DOMAIN_MAPPER
import com.utmaximur.data.places.NAMED_PLACE_LOCAL_MAPPER
import com.utmaximur.data.places.PlaceLocalMapper
import com.utmaximur.data.tracks.NAMED_TRACK_LOCAL_MAPPER
import com.utmaximur.data.tracks.TrackLocalMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
internal class MapperHolder(
    @Named(NAMED_TRACK_LOCAL_MAPPER)
    val trackLocalMapper: TrackLocalMapper,
    @Named(NAMED_DRINK_DOMAIN_MAPPER)
    val drinkDomainMapper: DrinkDomainMapper,
    @Named(NAMED_PLACE_LOCAL_MAPPER)
    val placeLocalMapper: PlaceLocalMapper,
)
