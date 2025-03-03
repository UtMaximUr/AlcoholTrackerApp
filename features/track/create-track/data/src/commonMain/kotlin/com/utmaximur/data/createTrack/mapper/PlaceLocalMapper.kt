package com.utmaximur.data.createTrack.mapper

import com.utmaximur.data.Mapper
import com.utmaximur.databaseRoom.place.DbPlace
import com.utmaximur.domain.models.Place
import org.koin.core.annotation.Factory

@Factory
internal class PlaceLocalMapper : Mapper<Place, DbPlace> {
    override fun transform(from: Place) = DbPlace(
        title = from.title,
        longitude = from.longitude,
        latitude = from.latitude,
        trackId = from.trackId
    )
}
