package com.utmaximur.data.statistic

import com.utmaximur.data.drinks.DrinkDomainMapper
import com.utmaximur.data.drinks.NAMED_DRINK_DOMAIN_MAPPER
import com.utmaximur.data.tracks.NAMED_TRACK_DOMAIN_MAPPER
import com.utmaximur.data.tracks.TrackDomainMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
internal class MapperHolder(
    @Named(NAMED_TRACK_DOMAIN_MAPPER)
    val trackDomainMapper: TrackDomainMapper,
    @Named(NAMED_DRINK_DOMAIN_MAPPER)
    val drinkDomainMapper: DrinkDomainMapper,
)
