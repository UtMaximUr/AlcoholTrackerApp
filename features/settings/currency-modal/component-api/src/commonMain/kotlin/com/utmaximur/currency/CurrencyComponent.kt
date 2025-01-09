package com.utmaximur.currency

import com.utmaximur.core.decompose.ComposeDialogComponent
import com.utmaximur.currency.store.Currency
import com.utmaximur.currency.store.CurrencyStore
import kotlinx.coroutines.flow.StateFlow


interface CurrencyComponent : ComposeDialogComponent {

    val model: StateFlow<CurrencyStore.State>

    fun onSelectClick(currency: Currency)
}