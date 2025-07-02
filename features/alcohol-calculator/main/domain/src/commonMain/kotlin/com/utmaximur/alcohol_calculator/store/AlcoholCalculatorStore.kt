package com.utmaximur.alcohol_calculator.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.alcohol_calculator.models.CalculateResult
import com.utmaximur.alcohol_calculator.models.DrinksData
import com.utmaximur.alcohol_calculator.models.Gender
import com.utmaximur.alcohol_calculator.store.AlcoholCalculatorStore.Intent
import com.utmaximur.alcohol_calculator.store.AlcoholCalculatorStore.Label
import com.utmaximur.alcohol_calculator.store.AlcoholCalculatorStore.State
import com.utmaximur.domain.ZERO_VALUE

interface AlcoholCalculatorStore : Store<Intent, State, Label> {

    data class State(
        val height: Int,
        val weight: Int,
        val gender: Gender,
        val drinksCount: Int
    ) {
        constructor() : this(
            height = ZERO_VALUE,
            weight = ZERO_VALUE,
            gender = Gender.NONE,
            drinksCount = ZERO_VALUE,
        )
    }

    sealed interface Intent {

        data class ChangeHeight(val height: String) : Intent

        data class ChangeWeight(val weight: String) : Intent

        data class ChangeGender(val gender: Gender) : Intent

        data object AddDrink : Intent

        data class Calculate(val drinksData: DrinksData) : Intent

    }

    sealed interface Label {

        data class ResultEvent(val result: CalculateResult) : Label
    }
}