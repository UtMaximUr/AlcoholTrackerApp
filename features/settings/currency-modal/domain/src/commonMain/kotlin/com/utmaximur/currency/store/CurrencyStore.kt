package com.utmaximur.currency.store

import com.arkivanov.mvikotlin.core.store.Store

interface CurrencyStore : Store<CurrencyStore.Intent, CurrencyStore.State, Nothing> {

    sealed interface Intent {
        data class SelectedCurrency(val currency: Currency) : Intent
    }

    data class State(
        val currencies: List<Currency>,
        val currentCurrency: Currency
    ) {
        constructor(): this(
            currencies = Currency.entries,
            currentCurrency = Currency.USD
        )
    }
}

enum class Currency {
    RUR,
    EUR,
    USD;
}