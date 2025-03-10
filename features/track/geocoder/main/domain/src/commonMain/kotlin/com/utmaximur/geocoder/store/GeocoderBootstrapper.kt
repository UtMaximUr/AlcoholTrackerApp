package com.utmaximur.geocoder.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper

internal sealed interface Action {
    data class GetPlace(val trackId: Long) : Action
}

internal class GeocoderBootstrapper(private val trackId: Long?) : CoroutineBootstrapper<Action>() {
    override fun invoke() {
        trackId?.let { id -> dispatch(Action.GetPlace(id)) }
    }
}
