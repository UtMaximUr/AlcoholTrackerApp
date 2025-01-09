package com.utmaximur.domain.models

import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.ZERO_VALUE_L

data class Icon(
    val id: Long = ZERO_VALUE_L,
    val url: String
) {
    companion object {
        val EMPTY = Icon(
            url = EMPTY_STRING
        )
    }
}