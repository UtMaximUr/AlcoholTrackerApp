package com.utmaximur.data.places

import com.utmaximur.data.Mapper
import com.utmaximur.databaseRoom.place.DbPlace
import com.utmaximur.domain.Place
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
@Named(NAMED_PLACE_DOMAIN_MAPPER)
internal class PlaceDomainMapper : Mapper<DbPlace, Place> {
    override fun transform(from: DbPlace) = Place(
        id = from.id,
        title = from.title,
        longitude = from.longitude,
        latitude = from.latitude,
        trackId = from.trackId,
    )
}