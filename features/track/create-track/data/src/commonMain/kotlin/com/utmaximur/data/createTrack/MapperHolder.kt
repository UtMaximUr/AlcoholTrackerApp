package com.utmaximur.data.createTrack

import com.utmaximur.data.drinks.DrinkUiMapper
import com.utmaximur.data.drinks.NAMED_DRINK_UI_MAPPER
import com.utmaximur.data.tracks.NAMED_TRACK_LOCAL_MAPPER
import com.utmaximur.data.tracks.TrackLocalMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
internal class MapperHolder(
    @Named(NAMED_TRACK_LOCAL_MAPPER)
    val trackLocalMapper: TrackLocalMapper,
    @Named(NAMED_DRINK_UI_MAPPER)
    val drinkUiMapper: DrinkUiMapper
)