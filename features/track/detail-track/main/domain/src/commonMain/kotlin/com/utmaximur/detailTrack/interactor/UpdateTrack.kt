package com.utmaximur.detailTrack.interactor

import com.utmaximur.domain.Interactor
import com.utmaximur.domain.ZERO_VALUE_STRING
import com.utmaximur.domain.detailTrack.DetailTrackRepository
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
            repository.updateTrack(params.toTrack())
        }
    }

    private fun Params.toTrack() = Track(
        id = trackId,
        drink = trackData.drink,
        quantity = trackData.quantity.ifEmpty { ZERO_VALUE_STRING }.toInt(),
        volume = trackData.volume.ifEmpty { ZERO_VALUE_STRING }.toFloat(),
        degree = trackData.degree.ifEmpty { ZERO_VALUE_STRING }.toFloat(),
        event = trackData.event,
        price = trackData.price.ifEmpty { ZERO_VALUE_STRING }.toFloat(),
        date = trackData.date.parseToLongNotNull()
    )

    data class Params(val trackId: Long, val trackData: TrackData)
}