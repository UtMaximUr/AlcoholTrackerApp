package com.utmaximur.createDrink.validation

import com.utmaximur.createDrink.DrinkData
import org.koin.core.annotation.Factory

internal fun interface Validator<in T : Any, out R> {
    fun validate(value: T): R
}

internal class FieldValidator : Validator<DrinkData, List<ValidationError>> {
    override fun validate(value: DrinkData): List<ValidationError> {
        return buildList {
            value.name.ifEmpty { add(ValidationError.NameEmpty) }
            value.photo.ifEmpty { add(ValidationError.PhotoUrlEmpty) }
            value.icon.url.ifEmpty { add(ValidationError.IconUrlEmpty) }
        }
    }
}

@Factory
internal class DrinkValidator(
    private val validator: Validator<DrinkData, List<ValidationError>> = FieldValidator()
) : Validator<DrinkData, DrinkValidatorResult> {
    override fun validate(value: DrinkData): DrinkValidatorResult {
        val errors: List<ValidationError> = validator.validate(value)
        return DrinkValidatorResult(value, errors)
    }
}

internal data class DrinkValidatorResult(
    val drinkData: DrinkData,
    val errors: List<ValidationError>
)