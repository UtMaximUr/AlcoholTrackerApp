package com.utmaximur.createDrink.validation

import createDrink.domain.resources.Res
import createDrink.domain.resources.icon_url_empty
import createDrink.domain.resources.name_empty
import createDrink.domain.resources.photo_url_empty
import org.jetbrains.compose.resources.StringResource

internal sealed interface ValidationError {

    val message: StringResource

    data object PhotoUrlEmpty : ValidationError {
        override val message: StringResource = Res.string.photo_url_empty
    }

    data object NameEmpty : ValidationError {
        override val message: StringResource = Res.string.name_empty
    }

    data object IconUrlEmpty : ValidationError {
        override val message: StringResource = Res.string.icon_url_empty
    }
}
