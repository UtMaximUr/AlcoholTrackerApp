package com.utmaximur.createTrack.interactor

import com.utmaximur.app.base.coroutines.NamedCoroutineScopeIO
import com.utmaximur.domain.Interactor
import com.utmaximur.domain.ZERO_VALUE_STRING
import com.utmaximur.domain.createTrack.CreateTrackRepository
import com.utmaximur.domain.models.Track
import com.utmaximur.domain.models.TrackData
import com.utmaximur.utils.extensions.parseToLongNotNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
internal class CreateTrack(
    createTrackRepository: Lazy<CreateTrackRepository>,
    @NamedCoroutineScopeIO
    private val ioScope: CoroutineScope,
) : Interactor<TrackData, Unit>() {

    private val repository by createTrackRepository

    override fun doWork(params: TrackData) {
        ioScope.launch { repository.saveTrack(params.toTrack()) }
    }

    private fun TrackData.toTrack() = Track(
        drink = drink,
        quantity = quantity.ifEmpty { ZERO_VALUE_STRING }.toInt(),
        volume = volume.ifEmpty { ZERO_VALUE_STRING }.toFloat(),
        degree = degree.ifEmpty { ZERO_VALUE_STRING }.toFloat(),
        event = event,
        price = price.ifEmpty { ZERO_VALUE_STRING }.toFloat(),
        date = date.parseToLongNotNull()
    )
}