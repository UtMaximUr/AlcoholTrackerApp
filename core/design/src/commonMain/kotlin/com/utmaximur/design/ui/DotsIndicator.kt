package com.utmaximur.design.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import design.resources.Res
import design.resources.ic_dot_18dp
import design.resources.ic_dot_default_18dp
import org.jetbrains.compose.resources.painterResource

@Composable
fun DotsIndicator(
    modifier: Modifier,
    totalDots: Int,
    selectedIndex: Int
) {
    LazyRow(
        modifier = modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(totalDots) { index ->
            if (index == selectedIndex) {
                Icon(
                    painter = painterResource(Res.drawable.ic_dot_18dp),
                    tint = MaterialTheme.colorScheme.tertiary,
                    contentDescription = null
                )
            } else {
                Icon(
                    painter = painterResource(Res.drawable.ic_dot_default_18dp),
                    tint = MaterialTheme.colorScheme.outline,
                    contentDescription = null
                )
            }
        }
    }
}