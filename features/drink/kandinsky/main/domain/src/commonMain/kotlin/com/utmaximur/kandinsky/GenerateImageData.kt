package com.utmaximur.kandinsky

import com.utmaximur.domain.EMPTY_STRING

data class GenerateImageData(
    val prompt: String,
    val style: String
) {

    class Builder {
        private var prompt: String = EMPTY_STRING
        private var style: String = EMPTY_STRING

        fun setPrompt(param: String) = apply { prompt = param }
        fun setStyle(param: String) = apply { style = param }

        fun build() = GenerateImageData(
            prompt = prompt,
            style = style
        )
    }
}