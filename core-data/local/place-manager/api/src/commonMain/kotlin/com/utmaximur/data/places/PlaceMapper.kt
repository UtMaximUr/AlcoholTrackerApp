package com.utmaximur.data.places

import com.utmaximur.data.Mapper
import com.utmaximur.databaseRoom.place.DbPlace
import com.utmaximur.domain.Place

typealias PlaceLocalMapper = Mapper<Place, DbPlace>
typealias PlaceDomainMapper = Mapper<DbPlace, Place>

const val NAMED_PLACE_LOCAL_MAPPER = "NAMED_PLACE_LOCAL_MAPPER"
const val NAMED_PLACE_DOMAIN_MAPPER = "NAMED_PLACE_DOMAIN_MAPPER"
