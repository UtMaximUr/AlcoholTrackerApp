package com.utmaximur.alcohol_calculator.models

data class DrinksData(
    val drinks: List<Drink>
) {
    class Builder {
        private val drinks: MutableMap<Int, Drink> = mutableMapOf()

        fun addVolume(index: Int, volume: String) {
            volume.toIntOrNull()?.takeIf { it >= 0 }?.let { safeValue ->
                drinks[index] = getDrink(index).copy(volume = safeValue)
            }
        }

        fun addAlcoholPercentage(index: Int, alcoholPercentage: String) {
            alcoholPercentage.toDoubleOrNull()?.takeIf { it >= 0 }?.let { safeValue ->
                drinks[index] = getDrink(index).copy(alcoholPercentage = safeValue)
            }
        }

        fun build() = DrinksData(
            drinks = drinks.values.filter { it.volume > 0 && it.alcoholPercentage > 0 }
        )

        private fun getDrink(index: Int): Drink {
            return drinks[index] ?: Drink.EMPTY
        }
    }
}