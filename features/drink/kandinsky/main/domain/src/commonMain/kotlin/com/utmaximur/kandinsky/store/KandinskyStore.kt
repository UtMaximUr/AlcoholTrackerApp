package com.utmaximur.kandinsky.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.domain.kandinsky.GenerationResult
import com.utmaximur.domain.kandinsky.ImageStyle
import com.utmaximur.kandinsky.GenerateImageData
import com.utmaximur.kandinsky.store.KandinskyScreenStore.Intent
import com.utmaximur.kandinsky.store.KandinskyScreenStore.Label
import com.utmaximur.kandinsky.store.KandinskyScreenStore.State
import com.utmaximur.kandinsky.validation.ValidationError

interface KandinskyScreenStore : Store<Intent, State, Label> {

    data class State(
        val internetAvailable: Boolean,
        val requestStylesUi: RequestUi<List<ImageStyle>>,
        val generationResult: GenerationResult
    ) {
        val isProgressStatus = generationResult.isStatusProgress
        val isDoneStatus = generationResult.isStatusDone

        constructor() : this(
            internetAvailable = true,
            requestStylesUi = RequestUi(),
            generationResult = GenerationResult.NONE
        )
    }

    sealed interface Intent {
        data class Generate(val data: GenerateImageData) : Intent
        data object RetryStyles : Intent
        data object GenerationCompletion : Intent
        data class ShowError(val errorMessage: String) : Intent
        data object Close : Intent
    }

    sealed interface Label {
        data class ValidatorError(val error: ValidationError) : Label
        data object CloseEvent : Label
    }
}
