package com.utmaximur.kandinsky.validation

import kandinsky.domain.resources.Res
import kandinsky.domain.resources.prompt_empty
import kandinsky.domain.resources.style_empty
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
