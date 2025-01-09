package com.utmaximur.createDrink.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.design.text.TextOutlinedLabel
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.domain.models.Icon
import createDrink.resources.Res
import createDrink.resources.select_icon
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DrinksIconContent(
    icons: List<Icon>,
    onSelectIcon: (Icon) -> Unit
) {
    var selectedIcon by remember { mutableStateOf(Icon.EMPTY) }
    ElevatedCardApp(
        contentPaddingValues = PaddingValues(12.dp)
    ) {
        TextOutlinedLabel(
            title = stringResource(Res.string.select_icon)
        )
        LazyVerticalGrid(
            modifier = Modifier
                .selectableGroup()
                .fillMaxSize(),
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(icons) { icon ->
                DrinkItem(
                    url = icon.url,
                    isSelected = selectedIcon == icon,
                    onItemClick = {
                        selectedIcon = icon
                        onSelectIcon(icon)
                    }
                )
            }
        }
    }
}