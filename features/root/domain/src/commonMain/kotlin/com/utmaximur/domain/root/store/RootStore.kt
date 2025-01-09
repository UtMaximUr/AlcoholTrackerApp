package com.utmaximur.domain.root.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.domain.root.store.RootStore.Intent
import com.utmaximur.domain.root.store.RootStore.Label
import com.utmaximur.domain.root.store.RootStore.State

interface RootStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class HandleBottomBarState(val isVisible: Boolean) : Intent
    }

    data class State(
        val isDarkTheme: Boolean,
        val isBottomBarVisible: Boolean
    ) {
        constructor(): this(
            isDarkTheme = false,
            isBottomBarVisible = true
        )
    }

    sealed interface Label
}