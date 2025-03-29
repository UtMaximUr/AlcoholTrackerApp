package com.utmaximur.design.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import design.resources.Res
import design.resources.cd_lock
import design.resources.ic_lock_24dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun trailingOrBlockedIcon(
    enabled: Boolean,
    trailingIcon: @Composable (() -> Unit)? = null,
): @Composable (() -> Unit)? {
    return when {
        enabled -> trailingIcon?.let { icon -> { icon.invoke() } }
        else -> {
            { FunctionalBlockedIcon() }
        }
    }
}

@Composable
private fun FunctionalBlockedIcon() = Icon(
    painter = painterResource(Res.drawable.ic_lock_24dp),
    contentDescription = stringResource(Res.string.cd_lock),
    tint = MaterialTheme.colorScheme.tertiary,
)