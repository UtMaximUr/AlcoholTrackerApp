package com.utmaximur.detailTrack.interactor

import com.utmaximur.domain.Interactor
import com.utmaximur.domain.detailTrack.DetailTrackRepository
import com.utmaximur.domain.models.Place
import com.utmaximur.domain.models.Track
import com.utmaximur.domain.models.TrackData
import com.utmaximur.utils.extensions.parseToLongNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
internal class UpdateTrack(
    detailTrackRepository: Lazy<DetailTrackRepository>
) : Interactor<UpdateTrack.Params, Unit>() {

    private val repository by detailTrackRepository

    override suspend fun doWork(params: Params) {
        withContext(Dispatchers.IO) {
            val track = params.track
            repository.updateTrack(params.transform(track))
            params.trackData.place.ifNotEmpty { place ->
                val updatedPlace = place.copy(trackId = track.id)
                repository.updatePlace(updatedPlace)
            }
        }
    }

    private fun Params.transform(track: Track) = Track(
        id = track.id,
        drink = trackData.drink,
        quantity = trackData.quantity.ifEmpty { track.quantity }.toInt(),
        volume = trackData.volume.ifEmpty { track.volume }.toFloat(),
        degree = trackData.degree.ifEmpty { track.degree }.toFloat(),
        event = trackData.event.ifEmpty { track.event },
        price = trackData.price.ifEmpty { track.price }.toFloat(),
        date = trackData.date.parseToLongNotNull()
    )

    private inline fun Place.ifNotEmpty(block: (Place) -> Unit) {
        if (this != Place.EMPTY) {
            block(this)
        }
    }

    private inline fun <T> String.ifEmpty(block: () -> T): String {
        return when {
            this.isEmpty() -> block().toString()
            else -> this
        }
    }

    internal data class Params(val track: Track, val trackData: TrackData)
}