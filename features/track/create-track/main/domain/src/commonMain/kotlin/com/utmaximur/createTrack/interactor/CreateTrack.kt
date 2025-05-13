package com.utmaximur.createTrack.interactor

import com.utmaximur.domain.Interactor
import com.utmaximur.domain.Track
import com.utmaximur.domain.TrackData
import com.utmaximur.domain.ZERO_VALUE_STRING
import com.utmaximur.domain.createTrack.CreateTrackRepository
import com.utmaximur.utils.extensions.decimalToFloat
import com.utmaximur.utils.extensions.parseToLongNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
internal class CreateTrack(
    createTrackRepository: Lazy<CreateTrackRepository>
) : Interactor<TrackData, Long>() {

    private val repository by createTrackRepository

    override suspend fun doWork(params: TrackData): Long {
        return withContext(Dispatchers.IO) {
            repository.saveTrack(params.toTrack())
        }
    }

    private fun TrackData.toTrack() = Track(
        drink = drink,
        quantity = quantity.ifEmpty { ZERO_VALUE_STRING }.toInt(),
        volume = volume.ifEmpty { ZERO_VALUE_STRING }.decimalToFloat(),
        degree = degree.ifEmpty { ZERO_VALUE_STRING }.decimalToFloat(),
        event = event,
        price = price.ifEmpty { ZERO_VALUE_STRING }.decimalToFloat(),
        date = date.parseToLongNotNull()
    )
}