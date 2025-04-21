package com.utmaximur.data.map.mappers

import com.utmaximur.data.Mapper
import com.utmaximur.databaseRoom.place.DbPlace
import com.utmaximur.domain.map.PlaceMark
import org.koin.core.annotation.Factory

@Factory
internal class PlaceMarkDomainMapper : Mapper<DbPlace, PlaceMark> {
    override fun transform(from: DbPlace) = PlaceMark(
        longitude = from.longitude,
        latitude = from.latitude,
        trackId = from.trackId,
    )
}