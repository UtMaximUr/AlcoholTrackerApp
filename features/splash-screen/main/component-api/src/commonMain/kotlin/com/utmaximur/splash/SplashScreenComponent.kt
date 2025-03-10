package com.utmaximur.splash

import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.splash.store.SplashScreenStore
import kotlinx.coroutines.flow.StateFlow

interface SplashScreenComponent : ComposeComponent {

    val model: StateFlow<SplashScreenStore.State>

    fun readyToLoad()

    sealed interface Output {

        data object NavigateToMainScreen : Output
    }
}
