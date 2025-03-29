package com.utmaximur.design.button

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import design.resources.Res
import design.resources.ic_save_button
import design.resources.cd_save
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SaveIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    tint: Color = MaterialTheme.colorScheme.tertiary,
    onClick: () -> Unit
) = IconButton(
    enabled = enabled,
    onClick = onClick
) {
    Icon(
        modifier = modifier,
        painter = painterResource(Res.drawable.ic_save_button),
        contentDescription = stringResource(Res.string.cd_save),
        tint = tint
    )
}