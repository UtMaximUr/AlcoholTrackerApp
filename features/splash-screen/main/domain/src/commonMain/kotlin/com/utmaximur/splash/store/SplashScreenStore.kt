package com.utmaximur.splash.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.splash.store.SplashScreenStore.Intent
import com.utmaximur.splash.store.SplashScreenStore.State
import com.utmaximur.splash.store.SplashScreenStore.Label

interface SplashScreenStore : Store<Intent, State, Label> {

    data object State

    sealed interface Intent {
        data object ReadyToLoad : Intent
    }

    sealed interface Label {
        data object MainScreen : Label
    }
}