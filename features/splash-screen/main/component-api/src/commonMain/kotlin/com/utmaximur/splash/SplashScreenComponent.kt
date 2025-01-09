package com.utmaximur.splash

import com.utmaximur.core.decompose.ComposeComponent

interface SplashScreenComponent : ComposeComponent {

    fun readyToLoad()

    sealed interface Output {

        data object NavigateToMainScreen : Output
    }
}