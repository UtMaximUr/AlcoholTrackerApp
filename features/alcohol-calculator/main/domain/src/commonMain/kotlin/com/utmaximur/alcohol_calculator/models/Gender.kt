package com.utmaximur.alcohol_calculator.models

enum class Gender {
    MALE, FEMALE, NONE;

    companion object {
        val uiValues = listOf(MALE, FEMALE)
        fun findGender(value: String): Gender {
            return when {
                value.isBlank() -> NONE
                else -> safeValueOf(value) ?: NONE
            }
        }

        private fun safeValueOf(value: String): Gender? {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
        }
    }
}