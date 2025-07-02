package com.utmaximur.alcohol_calculator.main_screen.integration

import com.utmaximur.alcohol_calculator.models.CalculateResult
import kotlinx.serialization.Serializable

@Serializable
sealed interface ModalConfiguration {
    @Serializable
    data class ResultDialog(val calculateResult: CalculateResult) : ModalConfiguration
}