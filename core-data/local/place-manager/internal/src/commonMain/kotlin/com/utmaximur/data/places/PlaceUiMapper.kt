package com.utmaximur.data.places

import com.utmaximur.data.Mapper
import com.utmaximur.databaseRoom.place.DbPlace
import com.utmaximur.domain.models.Drink
import com.utmaximur.domain.models.Track
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import com.utmaximur.databaseRoom.track.DbTrack
import com.utmaximur.domain.models.Place

@Factory
@Named(NAMED_PLACE_UI_MAPPER)
internal class PlaceUiMapper : Mapper<DbPlace, Place> {
    override fun transform(from: DbPlace) = Place(
        id = from.id,
        title = from.title,
        longitude = from.longitude,
        latitude = from.latitude,
        trackId = from.trackId
    )
}