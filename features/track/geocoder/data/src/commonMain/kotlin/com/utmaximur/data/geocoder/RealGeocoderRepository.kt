package com.utmaximur.data.geocoder

import com.utmaximur.app.base.app.ApplicationInfo
import com.utmaximur.data.geocoder.mapper.MapperHolder
import com.utmaximur.data.geocoder.network.GeocoderApi
import com.utmaximur.databaseRoom.place.PlaceDao
import com.utmaximur.domain.geocoder.GeocoderRepository
import com.utmaximur.domain.geocoder.SearchQuery
import com.utmaximur.domain.models.Place
import com.utmaximur.geocoder.BuildKonfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
internal class RealGeocoderRepository(
    private val geocoderApi: GeocoderApi,
    private val applicationInfo: ApplicationInfo,
    private val mapper: MapperHolder,
    private val placeDao: PlaceDao,
) : GeocoderRepository {

    override val mapEnabledState: Flow<Boolean> = flow {
        emit(applicationInfo.flavor.isMapAvailable())
    }

    override fun searchStream(query: SearchQuery): Flow<List<Place>> = flow {
        val searchResult = geocoderApi.getPlace(
            query = query.query,
            apikey = BuildKonfig.GEOCODER_KEY,
            lang = applicationInfo.language,
        ).features.map(mapper.placeRemoteMapper::transform)
        emit(searchResult)
    }.flowOn(Dispatchers.IO)

    override fun getPlaceByTrackId(trackId: Long): Flow<Place> = placeDao
        .getPlaceByTrackId(trackId)
        .filterNotNull()
        .map(mapper.placeUiMapper::transform)
}