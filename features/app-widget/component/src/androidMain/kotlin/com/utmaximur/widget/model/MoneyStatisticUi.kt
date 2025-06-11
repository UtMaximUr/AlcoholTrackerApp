package com.utmaximur.widget.model

internal data class MoneyStatisticUi(
    val titleResId: Int,
    private val amount: String,
    private val currency: String,
) {
    val formatAmount = "$amount $currency"
}