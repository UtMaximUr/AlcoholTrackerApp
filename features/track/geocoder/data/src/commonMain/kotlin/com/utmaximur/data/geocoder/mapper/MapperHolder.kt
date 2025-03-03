package com.utmaximur.data.geocoder.mapper

import com.utmaximur.data.places.NAMED_PLACE_UI_MAPPER
import com.utmaximur.data.places.PlaceUiMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
internal class MapperHolder(
    val placeRemoteMapper: PlaceRemoteMapper,
    @Named(NAMED_PLACE_UI_MAPPER)
    val placeUiMapper: PlaceUiMapper
)