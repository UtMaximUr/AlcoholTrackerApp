package com.utmaximur.alcohol_calculator.models

import kotlinx.serialization.Serializable

@Serializable
data class CalculateResult(
    val bac: Double,
    val eliminationTimeHh: Int,
    val eliminationTimeMm: Int,
)