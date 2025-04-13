package com.utmaximur.tracksModal.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.domain.Track
import com.utmaximur.domain.tracksModal.TracksModalRepository
import com.utmaximur.tracksModal.analytic_events.OpenScreenEvent
import com.utmaximur.tracksModal.store.TracksModalStore.Intent
import com.utmaximur.tracksModal.store.TracksModalStore.Label
import com.utmaximur.tracksModal.store.TracksModalStore.State
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


internal sealed interface Message {
    data class UpdateTracks(val tracks: List<Track>) : Message
    data class UpdateCurrency(val currency: String) : Message
}

internal class TracksModalExecutor(
    private val trackIds: List<Long>,
    private val repository: TracksModalRepository,
    private val analyticsManager: AnalyticsManager
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {

    override fun executeAction(action: Unit) {
        scope.launch { analyticsManager.trackEvent(OpenScreenEvent()) }
        repository.observeTracksByIds(trackIds)
            .onEach { tracks -> dispatch(Message.UpdateTracks(tracks)) }
            .launchIn(scope)
        repository.currencyStream
            .onEach { currency -> dispatch(Message.UpdateCurrency(currency)) }
            .launchIn(scope)
    }
}