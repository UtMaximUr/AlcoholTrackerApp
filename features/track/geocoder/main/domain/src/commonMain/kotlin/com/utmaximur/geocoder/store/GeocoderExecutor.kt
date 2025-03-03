package com.utmaximur.geocoder.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.geocoder.GeocoderRepository
import com.utmaximur.domain.geocoder.SearchQuery
import com.utmaximur.domain.models.Place
import com.utmaximur.geocoder.store.GeocoderStore.Intent
import com.utmaximur.geocoder.store.GeocoderStore.Label
import com.utmaximur.geocoder.store.GeocoderStore.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update


internal sealed interface Message {
    data class UpdateQuery(val query: String) : Message
    data class UpdatePlaces(val places: List<Place>) : Message
    data class UpdateSearchStarted(val searchStarted: Boolean) : Message
}

internal class GeocoderExecutor(
    private val geocoderRepository: GeocoderRepository,
) : CoroutineExecutor<Intent, Action, State, Message, Label>() {

    private val searchQuery = MutableStateFlow(EMPTY_STRING)
    private val debounceValue = 1_000L

    init {
        observeSearchQuery()
    }

    override fun executeAction(action: Action) {
        when (action) {
            is Action.GetPlace -> geocoderRepository
                .getPlaceByTrackId(action.trackId)
                .onEach { place -> dispatch(Message.UpdateQuery(place.title)) }
                .launchIn(scope)
        }
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.Search -> searchQuery.update { intent.query }
            is Intent.SelectedPlace -> dispatch(Message.UpdateQuery(intent.place.title))
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() = searchQuery
        .debounce(debounceValue)
        .map(::SearchQuery)
        .filter { it.isReadyToRequest }
        .onEach { dispatch(Message.UpdateSearchStarted(true)) }
        .flatMapLatest(geocoderRepository::searchStream)
        .onEach { places -> dispatch(Message.UpdatePlaces(places = places)) }
        .launchIn(scope)
}