package com.utmaximur.alcohol_calculator

import com.utmaximur.alcohol_calculator.models.DrinksData
import com.utmaximur.alcohol_calculator.models.Gender
import com.utmaximur.alcohol_calculator.store.AlcoholCalculatorStore
import com.utmaximur.core.decompose.ComposeComponent
import kotlinx.coroutines.flow.StateFlow

interface AlcoholCalculatorComponent : ComposeComponent {

    val model: StateFlow<AlcoholCalculatorStore.State>

    fun changeHeight(height: String)

    fun changeWeight(weight: String)

    fun changeGender(gender: Gender)

    fun onAddDrinkClick()

    fun onCalculateClick(drinksData: DrinksData)

}
