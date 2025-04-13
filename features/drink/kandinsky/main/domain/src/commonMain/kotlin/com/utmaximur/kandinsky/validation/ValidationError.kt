package com.utmaximur.kandinsky.validation

import features.drink.kandinsky.main.domain.Res
import features.drink.kandinsky.main.domain.prompt_empty
import features.drink.kandinsky.main.domain.style_empty
import org.jetbrains.compose.resources.StringResource

internal sealed interface ValidationError {

    val message: StringResource

    data object PromptEmpty : ValidationError {
        override val message: StringResource = Res.string.prompt_empty
    }

    data object StyleEmpty : ValidationError {
        override val message: StringResource = Res.string.style_empty
    }
}
