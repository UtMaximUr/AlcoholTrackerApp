package com.utmaximur.design.modal

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetApp(
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) = ModalBottomSheet(
    onDismissRequest = onDismissRequest,
    containerColor = containerColor,
    contentColor = MaterialTheme.colorScheme.primary,
    sheetState = rememberModalBottomSheetState(true),
    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
    content = content
)