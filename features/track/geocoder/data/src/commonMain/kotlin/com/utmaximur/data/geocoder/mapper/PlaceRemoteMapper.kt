package com.utmaximur.data.geocoder.mapper

import com.utmaximur.data.Mapper
import com.utmaximur.data.geocoder.network.Feature
import com.utmaximur.domain.models.Place
import org.koin.core.annotation.Factory

@Factory
class PlaceRemoteMapper : Mapper<Feature, Place> {
    override fun transform(from: Feature): Place {
        val properties = from.properties
        val coordinates = from.geometry.coordinates
        return Place(
            title = "${properties.name} ${properties.description}",
            longitude = coordinates.first(),
            latitude = coordinates.last(),
        )
    }
}
