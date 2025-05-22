package com.utmaximur.currency.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.utmaximur.currency.CurrencyComponent
import com.utmaximur.design.modal.ModalBottomSheetApp

@Composable
internal fun CurrencySelectBottomSheet(
    component: CurrencyComponent,
) {
    val state by component.model.collectAsState()

    ModalBottomSheetApp(
        onDismissRequest = component::dismiss,
    ) {
        LazyColumn(contentPadding = PaddingValues(vertical = 16.dp)) {
            items(state.currencies, key = { it.ordinal }) { item ->
                CurrencyItem(
                    currency = item,
                    isSelected = state.currentCurrency == item,
                    onClick = { component.onSelectClick(item) },
                )
            }
        }
    }
}
