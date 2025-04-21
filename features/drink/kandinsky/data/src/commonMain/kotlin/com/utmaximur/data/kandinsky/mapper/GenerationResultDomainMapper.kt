package com.utmaximur.data.kandinsky.mapper

import com.utmaximur.data.Mapper
import com.utmaximur.data.kandinsky.network.models.GenerateResult
import com.utmaximur.data.kandinsky.network.models.GenerateStatus
import com.utmaximur.domain.kandinsky.GenerationResult
import com.utmaximur.domain.kandinsky.GenerationStatus
import org.koin.core.annotation.Factory

@Factory
internal class GenerationResultDomainMapper : Mapper<GenerateResult, GenerationResult> {
    override fun transform(from: GenerateResult) = GenerationResult(
        status = convertStatus(from.status),
        imagePathFile = from.result.files.firstOrNull().orEmpty(),
        censored = from.result.censored
    )

    private fun convertStatus(status: GenerateStatus): GenerationStatus =
        when (status) {
            GenerateStatus.NONE -> GenerationStatus.NONE
            GenerateStatus.INITIAL, GenerateStatus.PROCESSING -> GenerationStatus.IN_PROGRESS
            GenerateStatus.DONE -> GenerationStatus.DONE
            GenerateStatus.FAIL -> GenerationStatus.FAIL
        }
}