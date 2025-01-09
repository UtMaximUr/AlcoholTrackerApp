package com.utmaximur.data.tracks

import com.utmaximur.data.Mapper
import com.utmaximur.domain.models.Track
import com.utmaximur.databaseRoom.track.DbTrack


typealias TrackLocalMapper = Mapper<Track, DbTrack>
typealias TrackUiMapper = Mapper<DbTrack, Track>

const val NAMED_TRACK_LOCAL_MAPPER = "NAMED_TRACK_LOCAL_MAPPER"
const val NAMED_TRACK_UI_MAPPER = "NAMED_TRACK_UI_MAPPER"