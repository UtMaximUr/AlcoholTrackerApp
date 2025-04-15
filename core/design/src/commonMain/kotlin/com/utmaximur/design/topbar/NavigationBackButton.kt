package com.utmaximur.design.topbar

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import design.resources.Res
import design.resources.cd_back
import design.resources.ic_back_button
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun NavigationBackButton(onBackClick: () -> Unit) = IconButton(onClick = onBackClick) {
    Icon(
        painter = painterResource(Res.drawable.ic_back_button),
        contentDescription = stringResource(Res.string.cd_back),
    )
}