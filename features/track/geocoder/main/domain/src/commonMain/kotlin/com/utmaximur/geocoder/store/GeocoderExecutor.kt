package com.utmaximur.geocoder.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.utmaximur.core.mvi_mapper.Request
import com.utmaximur.core.mvi_mapper.asRequest
import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.geocoder.GeocoderRepository
import com.utmaximur.domain.geocoder.Place
import com.utmaximur.domain.geocoder.SearchQuery
import com.utmaximur.geocoder.interactor.SavePlaceToTrack
import com.utmaximur.geocoder.store.GeocoderStore.Intent
import com.utmaximur.geocoder.store.GeocoderStore.Label
import com.utmaximur.geocoder.store.GeocoderStore.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal sealed interface Message {
    data class UpdateSelectedPlace(val selectedPlace: Place) : Message
    data class UpdatePlaces(val requestPlacesUi: Request<List<Place>>) : Message
    data class UpdateSearchStarted(val searchStarted: Boolean) : Message
    data class UpdateMapState(val isMapEnabled: Boolean) : Message
}

internal class GeocoderExecutor(
    private val geocoderRepository: GeocoderRepository,
    private val interactor: SavePlaceToTrack
) : CoroutineExecutor<Intent, Action, State, Message, Label>() {

    private val searchQuery = MutableStateFlow(EMPTY_STRING)
    private val debounceTime = 1_000L

    init {
        observeMapEnabledState()
        observeSearchQuery()
    }

    override fun executeAction(action: Action) {
        when (action) {
            is Action.GetPlace -> fetchPlace(action.trackId)
        }
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.SearchPlace -> searchQuery.update { intent.query }
            is Intent.SelectedPlace -> dispatch(Message.UpdateSelectedPlace(intent.place))
            is Intent.SavePlace -> savePlaceToTrack(intent.trackId)
        }
    }

    private fun observeMapEnabledState() = geocoderRepository
        .mapEnabledState
        .onEach { enabled -> dispatch(Message.UpdateMapState(enabled)) }
        .launchIn(scope)

    private fun observeSearchQuery() = searchQuery
        .setupDebounce()
        .handleSearch()
        .launchIn(scope)

    @OptIn(FlowPreview::class)
    private fun Flow<String>.setupDebounce() = this
        .debounce(debounceTime)
        .map(::SearchQuery)
        .filter { it.isReadyToRequest }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<SearchQuery>.handleSearch() = this
        .onEach { dispatch(Message.UpdateSearchStarted(true)) }
        .flatMapLatest(geocoderRepository::searchStream)
        .asRequest()
        .onEach { places -> dispatch(Message.UpdatePlaces(places)) }

    private fun fetchPlace(trackId: Long) = geocoderRepository
        .getPlaceByTrackId(trackId)
        .onEach { place -> dispatch(Message.UpdateSelectedPlace(place)) }
        .launchIn(scope)

    private fun savePlaceToTrack(trackId: Long) {
        val selectedPlace = state().selectedPlace ?: run {
            println("Attempted to save place with null selectedPlace")
            return
        }
        scope.launch {
            val params = SavePlaceToTrack.Params(trackId = trackId, place = selectedPlace)
            interactor.doWork(params)
        }
    }
}
