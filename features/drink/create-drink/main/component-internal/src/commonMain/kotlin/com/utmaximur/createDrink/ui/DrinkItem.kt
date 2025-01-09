package com.utmaximur.createDrink.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.utmaximur.design.extensions.bounceClick
import createDrink.resources.Res
import createDrink.resources.cd_drink_icon
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DrinkItem(
    url: String,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        Column(
            modifier = Modifier
                .bounceClick()
                .selectable(
                    selected = isSelected,
                    onClick = onItemClick,
                    role = Role.Image
                )
                .alpha(if (isSelected) DefaultAlpha else 0.2f)
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AsyncImage(
                modifier = Modifier.size(48.dp),
                model = url,
                contentDescription = stringResource(Res.string.cd_drink_icon)
            )
        }
    }
}