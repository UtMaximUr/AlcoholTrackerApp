package com.utmaximur.kandinsky.validation

import com.utmaximur.domain.Validator
import com.utmaximur.kandinsky.GenerateImageData
import org.koin.core.annotation.Factory

internal class FieldValidator : Validator<GenerateImageData, List<ValidationError>> {
    override fun validate(value: GenerateImageData): List<ValidationError> {
        return buildList {
            value.prompt.ifEmpty { add(ValidationError.PromptEmpty) }
            value.style.ifEmpty { add(ValidationError.StyleEmpty) }
        }
    }
}

@Factory
internal class RequestValidator(
    private val validator: Validator<GenerateImageData, List<ValidationError>> = FieldValidator()
) : Validator<GenerateImageData, RequestValidatorResult> {
    override fun validate(value: GenerateImageData): RequestValidatorResult {
        val errors: List<ValidationError> = validator.validate(value)
        return RequestValidatorResult(value, errors)
    }
}

internal data class RequestValidatorResult(
    val generateImageData: GenerateImageData,
    val errors: List<ValidationError>
)