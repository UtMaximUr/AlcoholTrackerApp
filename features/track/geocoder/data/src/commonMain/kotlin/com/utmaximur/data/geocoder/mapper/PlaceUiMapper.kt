package com.utmaximur.data.geocoder.mapper

import com.utmaximur.data.Mapper
import com.utmaximur.data.geocoder.network.Feature
import com.utmaximur.databaseRoom.place.DbPlace
import com.utmaximur.domain.models.Place
import org.koin.core.annotation.Factory

@Factory
class PlaceUiMapper : Mapper<DbPlace, Place> {
    override fun transform(from: DbPlace) = Place(
        id = from.id,
        title = from.title,
        longitude = from.longitude,
        latitude = from.latitude,
        trackId = from.trackId
    )
}