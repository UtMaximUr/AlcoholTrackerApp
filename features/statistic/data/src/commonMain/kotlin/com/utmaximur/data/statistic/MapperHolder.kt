package com.utmaximur.data.statistic

import com.utmaximur.data.drinks.DrinkUiMapper
import com.utmaximur.data.drinks.NAMED_DRINK_UI_MAPPER
import com.utmaximur.data.tracks.NAMED_TRACK_UI_MAPPER
import com.utmaximur.data.tracks.TrackUiMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
internal class MapperHolder(
    @Named(NAMED_TRACK_UI_MAPPER)
    val trackUiMapper: TrackUiMapper,
    @Named(NAMED_DRINK_UI_MAPPER)
    val drinkUiMapper: DrinkUiMapper
)