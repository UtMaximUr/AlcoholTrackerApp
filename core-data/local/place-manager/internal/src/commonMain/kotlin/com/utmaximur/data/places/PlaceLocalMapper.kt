package com.utmaximur.data.places

import com.utmaximur.data.Mapper
import com.utmaximur.domain.models.Track
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import com.utmaximur.databaseRoom.drink.DbDrink
import com.utmaximur.databaseRoom.place.DbPlace
import com.utmaximur.databaseRoom.track.DbTrack
import com.utmaximur.domain.models.Place

@Factory
@Named(NAMED_PLACE_LOCAL_MAPPER)
internal class PlaceLocalMapper : Mapper<Place, DbPlace> {
    override fun transform(from: Place) = DbPlace(
        title = from.title,
        longitude = from.longitude,
        latitude = from.latitude,
        trackId = from.trackId
    )
}