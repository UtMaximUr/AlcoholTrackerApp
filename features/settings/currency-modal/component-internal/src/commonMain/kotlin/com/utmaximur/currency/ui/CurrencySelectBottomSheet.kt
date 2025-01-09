package com.utmaximur.currency.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.utmaximur.currency.CurrencyComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CurrencySelectBottomSheet(
    component: CurrencyComponent
) {
    val state by component.model.collectAsState()

    ModalBottomSheet(
        onDismissRequest = component::dismiss,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        sheetState = rememberModalBottomSheetState(true),
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
    ) {
        LazyColumn(contentPadding = PaddingValues(vertical = 16.dp)) {
            items(state.currencies) { item ->
                CurrencyItem(
                    currency = item,
                    isSelected = state.currentCurrency == item,
                    onClick = { component.onSelectClick(item) }
                )
            }
        }
    }
}