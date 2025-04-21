package com.utmaximur.sortingDrinks.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.design.button.SaveIconButton
import com.utmaximur.design.extensions.bottomFade
import com.utmaximur.design.extensions.fadingEdge
import com.utmaximur.design.topbar.TopBar
import com.utmaximur.sortingDrinks.SortingDrinksComponent
import features.settings.sorting_drinks.main.Res
import features.settings.sorting_drinks.main.title_sorting_drinks
import org.jetbrains.compose.resources.stringResource
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
internal fun SortingDrinksScreen(
    component: SortingDrinksComponent,
) {
    val state by component.model.collectAsState()
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        component.moveFromTo(from.index, to.index)
    }

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = component::navigateBack,
                title = stringResource(Res.string.title_sorting_drinks),
                actions = {
                    SaveIconButton { component.onSaveClick() }
                }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .fadingEdge(bottomFade),
                state = lazyListState,
                contentPadding = PaddingValues(vertical = 1.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(state.sortedDrinks, key = { it.id }) { sortedDrink ->
                    ReorderableItem(reorderableLazyListState, key = sortedDrink.id) { isDragging ->
                        SortingDrinkItem(
                            draggableModifier = Modifier.draggableHandle(),
                            drinkName = sortedDrink.name,
                            imageUrl = sortedDrink.imageUrl,
                            isDragging = isDragging
                        )
                    }
                }
            }
        }
    )
}