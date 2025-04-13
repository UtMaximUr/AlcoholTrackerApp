package com.utmaximur.createDrink.validation

import features.drink.create_drink.main.domain.Res
import features.drink.create_drink.main.domain.icon_url_empty
import features.drink.create_drink.main.domain.name_empty
import features.drink.create_drink.main.domain.photo_url_empty
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
