package com.utmaximur.data.tracks

import com.utmaximur.data.Mapper
import com.utmaximur.databaseRoom.track.DbTrack
import com.utmaximur.domain.Drink
import com.utmaximur.domain.Track
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
@Named(NAMED_TRACK_UI_MAPPER)
class TrackUiMapper : Mapper<DbTrack, Track> {
    override fun transform(from: DbTrack) = Track(
        id = from.id,
        drink = Drink(
            id = from.drink.id,
            name = from.drink.name,
            icon = from.drink.icon,
            photo = from.drink.photo,
        ),
        volume = from.volume,
        quantity = from.quantity,
        degree = from.degree,
        event = from.event,
        price = from.price,
        date = from.date,
    )
}
