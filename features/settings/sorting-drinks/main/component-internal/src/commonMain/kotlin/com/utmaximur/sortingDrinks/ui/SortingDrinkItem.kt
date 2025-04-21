package com.utmaximur.sortingDrinks.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.utmaximur.design.ui.ImageLoaderContent
import features.settings.sorting_drinks.main.Res
import features.settings.sorting_drinks.main.cd_move
import features.settings.sorting_drinks.main.ic_move_24dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SortingDrinkItem(
    draggableModifier: Modifier,
    isDragging: Boolean,
    drinkName: String,
    imageUrl: String,
) {
    val elevation by animateDpAsState(
        if (isDragging) 16.dp else 0.dp,
        label = drinkName
    )
    Box(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .shadow(elevation)
            .background(MaterialTheme.colorScheme.primaryContainer),
    ) {
        ImageLoaderContent(
            imageUrl = imageUrl,
            contentDescription = drinkName,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .aspectRatio(3f)
                .clip(AbsoluteCutCornerShape(topRightPercent = 100)),
        )
        Text(
            text = drinkName,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
        )
        Icon(
            painter = painterResource(Res.drawable.ic_move_24dp),
            contentDescription = stringResource(Res.string.cd_move),
            tint = MaterialTheme.colorScheme.secondary,
            modifier = draggableModifier
                .padding(horizontal = 12.dp)
                .align(Alignment.CenterEnd)
                .size(24.dp)
        )
    }
}