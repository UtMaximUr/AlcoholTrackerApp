package com.utmaximur.data.detailTrack

import com.utmaximur.data.places.NAMED_PLACE_LOCAL_MAPPER
import com.utmaximur.data.places.PlaceLocalMapper
import com.utmaximur.data.tracks.NAMED_TRACK_LOCAL_MAPPER
import com.utmaximur.data.tracks.NAMED_TRACK_DOMAIN_MAPPER
import com.utmaximur.data.tracks.TrackLocalMapper
import com.utmaximur.data.tracks.TrackDomainMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
internal class MapperHolder(
    @Named(NAMED_TRACK_LOCAL_MAPPER)
    val trackLocalMapper: TrackLocalMapper,
    @Named(NAMED_TRACK_DOMAIN_MAPPER)
    val trackDomainMapper: TrackDomainMapper,
    @Named(NAMED_PLACE_LOCAL_MAPPER)
    val placeLocalMapper: PlaceLocalMapper,
)
