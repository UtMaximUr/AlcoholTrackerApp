package com.utmaximur.design.button

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import design.resources.Res
import design.resources.ic_add_fab
import design.resources.cd_add_action
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddFloatingActionButton(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.tertiary,
    onClick: () -> Unit
) = FloatingActionButton(
    modifier = modifier,
    containerColor = containerColor,
    onClick = onClick
) {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(Res.drawable.ic_add_fab),
        contentDescription = stringResource(Res.string.cd_add_action),
        tint = Color.White
    )
}
