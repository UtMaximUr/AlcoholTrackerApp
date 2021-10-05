package com.utmaximur.alcoholtracker.data.mapper

import com.utmaximur.alcoholtracker.data.dbo.TrackDBO
import com.utmaximur.alcoholtracker.domain.entity.Track
import javax.inject.Inject

class TrackMapper @Inject constructor() {

    fun map(dbo: TrackDBO): Track {
        return Track(
            id = dbo.id,
            drink = dbo.drink,
            volume = dbo.volume,
            quantity = dbo.quantity,
            degree = dbo.degree,
            event = dbo.event,
            price = dbo.price,
            date = dbo.date,
            icon = dbo.icon
        )
    }

    fun map(domain: Track): TrackDBO {
        return TrackDBO(
            id = domain.id,
            drink = domain.drink,
            volume = domain.volume,
            quantity = domain.quantity,
            degree = domain.degree,
            event = domain.event,
            price = domain.price,
            date = domain.date,
            icon = domain.icon
        )
    }
}