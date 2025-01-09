package com.utmaximur.currency.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.currency.CurrencyComponent
import com.utmaximur.currency.store.Currency
import com.utmaximur.currency.store.CurrencyStore
import com.utmaximur.currency.ui.CurrencySelectBottomSheet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultCurrencyComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val dismissCallback: () -> Unit
) : CurrencyComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: CurrencyStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<CurrencyStore.State> = store.stateFlow

    override fun dismiss() = dismissCallback()

    override fun onSelectClick(currency: Currency) =
        store.accept(CurrencyStore.Intent.SelectedCurrency(currency))

    @Composable
    override fun Render(modifier: Modifier) = CurrencySelectBottomSheet(this)
}