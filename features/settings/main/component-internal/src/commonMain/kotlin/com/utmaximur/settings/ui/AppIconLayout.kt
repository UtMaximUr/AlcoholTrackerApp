package com.utmaximur.settings.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import settings.resources.Res
import settings.resources.cd_icon_app
import settings.resources.ic_launcher_foreground

@Composable
internal fun AppIconLayout(
    modifier: Modifier = Modifier
) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {
    Image(
        modifier = Modifier
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.tertiary)
            .size(164.dp),
        painter = painterResource(Res.drawable.ic_launcher_foreground),
        contentDescription = stringResource(Res.string.cd_icon_app)
    )
}