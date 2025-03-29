package com.utmaximur.domain.kandinsky

data class GenerationResult(
    val status: GenerationStatus,
    val imagePathFile: String,
    val censored: Boolean = false
) {
    val isStatusProgress = status == GenerationStatus.IN_PROGRESS
    val isStatusDone = status == GenerationStatus.DONE
    companion object {
        val NONE = GenerationResult(
            status = GenerationStatus.NONE,
            imagePathFile = ""
        )
    }
}