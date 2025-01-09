package com.utmaximur.createDrink

import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.createDrink.store.CreateDrinkStore
import kotlinx.coroutines.flow.StateFlow

interface CreateDrinkComponent : ComposeComponent {

    val model: StateFlow<CreateDrinkStore.State>

    fun navigateBack()

    fun onSaveClick(drinkData: DrinkData)

    fun openImageActionDialog()

    sealed interface Output {

        data object OpenImageActionsDialog : Output

        data object NavigateBack : Output
    }
}