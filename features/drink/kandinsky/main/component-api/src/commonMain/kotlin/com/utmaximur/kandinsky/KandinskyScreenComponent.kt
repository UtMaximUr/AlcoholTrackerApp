package com.utmaximur.kandinsky

import com.utmaximur.core.decompose.ComposeComponent
import kotlinx.coroutines.flow.StateFlow
import com.utmaximur.kandinsky.store.KandinskyScreenStore

interface KandinskyScreenComponent : ComposeComponent {

    val model: StateFlow<KandinskyScreenStore.State>

    fun navigateBack()

    fun generateImage()

    fun retryStylesLoading()

    fun applyGeneratedImage()

    sealed interface Output {

        data object NavigateBack : Output
    }
}
