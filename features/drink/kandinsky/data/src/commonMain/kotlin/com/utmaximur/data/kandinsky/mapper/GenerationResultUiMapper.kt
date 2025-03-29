package com.utmaximur.data.kandinsky.mapper

import com.utmaximur.data.Mapper
import com.utmaximur.data.kandinsky.network.models.GenerateResult
import com.utmaximur.data.kandinsky.network.models.GenerateStatus
import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.kandinsky.GenerationResult
import com.utmaximur.domain.kandinsky.GenerationStatus
import org.koin.core.annotation.Factory

@Factory
internal class GenerationResultUiMapper : Mapper<GenerateResult, GenerationResult> {
    override fun transform(from: GenerateResult) = GenerationResult(
        status = convertStatus(from.status),
        imagePathFile = from.images.firstOrNull() ?: EMPTY_STRING,
        censored = from.censored
    )

    private fun convertStatus(status: GenerateStatus): GenerationStatus =
        when (status) {
            GenerateStatus.NONE -> GenerationStatus.NONE
            GenerateStatus.INITIAL, GenerateStatus.PROCESSING -> GenerationStatus.IN_PROGRESS
            GenerateStatus.DONE -> GenerationStatus.DONE
            GenerateStatus.FAIL -> GenerationStatus.FAIL
        }
}