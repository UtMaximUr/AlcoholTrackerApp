package com.utmaximur.sortingDrinks

import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.sortingDrinks.store.SortingDrinksStore
import kotlinx.coroutines.flow.StateFlow

interface SortingDrinksComponent : ComposeComponent {

    val model: StateFlow<SortingDrinksStore.State>

    fun navigateBack()

    fun moveFromTo(from: Int, to: Int)

    fun onSaveClick()
}
