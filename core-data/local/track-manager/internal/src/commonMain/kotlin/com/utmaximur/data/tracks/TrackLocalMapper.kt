package com.utmaximur.data.tracks

import com.utmaximur.data.Mapper
import com.utmaximur.domain.models.Track
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import com.utmaximur.databaseRoom.drink.DbDrink
import com.utmaximur.databaseRoom.track.DbTrack

@Factory
@Named(NAMED_TRACK_LOCAL_MAPPER)
internal class TrackLocalMapper : Mapper<Track, DbTrack> {
    override fun transform(from: Track) = DbTrack(
        id = from.id,
        drink = DbDrink(
            id = from.drink.id,
            name = from.drink.name,
            icon = from.drink.icon,
            photo = from.drink.photo
        ),
        volume = from.volume,
        quantity = from.quantity,
        degree = from.degree,
        event = from.event,
        price = from.price,
        date = from.date
    )
}