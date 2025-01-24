package com.utmaximur.domain.models

import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.ZERO_VALUE_L
import kotlinx.datetime.LocalDateTime

data class Drink(
    val id: Long = ZERO_VALUE_L,
    val name: String,
    val icon: String,
    val photo: String,
    val createdAt: LocalDateTime? = null
) {
    val isUserCreated = createdAt != null
    companion object {
        val EMPTY = Drink(
            id = ZERO_VALUE_L,
            name = EMPTY_STRING,
            icon = EMPTY_STRING,
            photo = EMPTY_STRING
        )
    }
}