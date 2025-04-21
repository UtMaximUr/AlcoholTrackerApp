package com.utmaximur.data.tracks

import com.utmaximur.data.Mapper
import com.utmaximur.databaseRoom.track.DbTrack
import com.utmaximur.domain.Track

typealias TrackLocalMapper = Mapper<Track, DbTrack>
typealias TrackDomainMapper = Mapper<DbTrack, Track>

const val NAMED_TRACK_LOCAL_MAPPER = "NAMED_TRACK_LOCAL_MAPPER"
const val NAMED_TRACK_DOMAIN_MAPPER = "NAMED_TRACK_DOMAIN_MAPPER"
