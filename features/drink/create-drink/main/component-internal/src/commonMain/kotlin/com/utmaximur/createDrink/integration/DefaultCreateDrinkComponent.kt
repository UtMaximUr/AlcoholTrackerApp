package com.utmaximur.createDrink.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.createDrink.CreateDrinkComponent
import com.utmaximur.createDrink.DrinkData
import com.utmaximur.createDrink.store.CreateDrinkStore
import com.utmaximur.createDrink.ui.CreateDrinkScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultCreateDrinkComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val output: (CreateDrinkComponent.Output) -> Unit
) : CreateDrinkComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: CreateDrinkStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<CreateDrinkStore.State> = store.stateFlow

    override fun navigateBack() = output(
        CreateDrinkComponent.Output.NavigateBack
    )

    override fun onSaveClick(drinkData: DrinkData) =
        store.accept(CreateDrinkStore.Intent.SaveDrinkData(drinkData))

    override fun openImageActionDialog() = output(
        CreateDrinkComponent.Output.OpenImageActionsDialog
    )

    init {
        store.labels.onEach { event ->
            when (event) {
                is CreateDrinkStore.Label.CloseEvent ->
                    output(CreateDrinkComponent.Output.NavigateBack)
            }
        }.launchIn(coroutineScope())
    }

    @Composable
    override fun Render(modifier: Modifier) = CreateDrinkScreen(this)

}