package com.utmaximur.sortingDrinks.integration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.bottombar.LocalBottomBarController
import com.utmaximur.sortingDrinks.SortingDrinksComponent
import com.utmaximur.sortingDrinks.store.SortingDrinksStore
import com.utmaximur.sortingDrinks.ui.SortingDrinksScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultSortingDrinksComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val back: () -> Unit,
) : SortingDrinksComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: SortingDrinksStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SortingDrinksStore.State> = store.stateFlow

    override fun navigateBack() = back()

    override fun moveFromTo(from: Int, to: Int) =
        store.accept(SortingDrinksStore.Intent.Move(from, to))

    override fun onSaveClick() =
        store.accept(SortingDrinksStore.Intent.SaveSortingDrinks)

    init {
        store.labels.onEach { event ->
            when (event) {
                is SortingDrinksStore.Label.CloseEvent -> back()
            }
        }.launchIn(coroutineScope())
    }

    @Composable
    override fun Render(modifier: Modifier) {
        val bottomBarController = LocalBottomBarController.current
        LaunchedEffect(Unit) {
            bottomBarController.hideToLifecycle(lifecycle)
        }
        SortingDrinksScreen(this)
    }
}
