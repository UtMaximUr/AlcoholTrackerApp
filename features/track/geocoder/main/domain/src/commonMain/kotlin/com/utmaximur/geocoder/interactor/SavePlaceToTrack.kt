package com.utmaximur.geocoder.interactor

import com.utmaximur.domain.Interactor
import com.utmaximur.domain.geocoder.GeocoderRepository
import com.utmaximur.domain.geocoder.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
internal class SavePlaceToTrack(
    geocoderRepository: Lazy<GeocoderRepository>
) : Interactor<SavePlaceToTrack.Params, Unit>() {

    private val repository by geocoderRepository

    override suspend fun doWork(params: Params) {
        withContext(Dispatchers.IO) {
            val place = params.place.copy(trackId = params.trackId)
            repository.savePlace(place)
        }
    }

    internal data class Params(val trackId: Long, val place: Place)
}