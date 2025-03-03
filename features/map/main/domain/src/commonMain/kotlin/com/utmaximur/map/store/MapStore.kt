package com.utmaximur.map.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.domain.models.Place
import com.utmaximur.map.store.MapStore.State

interface MapStore : Store<Unit, State, Unit> {

    data class State(
        val places: List<Place>,
        val isDarkTheme: Boolean
    ) {
        constructor() : this(
            places = emptyList(),
            isDarkTheme = false
        )
    }
}