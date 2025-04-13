package com.utmaximur.data.places

import com.utmaximur.data.Mapper
import com.utmaximur.databaseRoom.place.DbPlace
import com.utmaximur.domain.Place
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
@Named(NAMED_PLACE_LOCAL_MAPPER)
internal class PlaceLocalMapper : Mapper<Place, DbPlace> {
    override fun transform(from: Place) = DbPlace(
        title = from.title,
        longitude = from.longitude,
        latitude = from.latitude,
        trackId = from.trackId,
    )
}
