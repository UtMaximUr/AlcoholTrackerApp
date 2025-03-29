package com.utmaximur.data.kandinsky.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerateResult(
    @SerialName("model_status")
    val modelStatus: String = "",
    val uuid: String = "",
    val status: GenerateStatus = GenerateStatus.INITIAL,
    val images: List<String> = listOf(),
    val censored: Boolean = false,
    val generationTime: Int = 0
)

/**
 *  [INITIAL]- запрос получен, находится в очереди на обработку
 *  [PROCESSING] - запрос находится в процессе обработки
 *  [DONE]- задание выполнено
 *  [FAIL] - задание не удалось выполнить.
 */

@Serializable
enum class GenerateStatus {
    NONE,
    INITIAL,
    PROCESSING,
    DONE,
    FAIL
}