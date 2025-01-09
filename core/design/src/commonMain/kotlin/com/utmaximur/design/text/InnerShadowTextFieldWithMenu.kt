package com.utmaximur.design.text

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import design.resources.Res
import design.resources.cd_menu
import design.resources.ic_chevrone_bottom
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InnerShadowTextFieldWithMenu(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    title: String? = null,
    options: List<String>?,
    placeholderText: String = title.orEmpty(),
    leadingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    onFocusChanged: (String) -> Unit = {},
    onValueChange: (String) -> Unit = {},
    showShimmer: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    isError: Boolean = false,
    minLines: Int = 1,
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }) {
        InnerShadowTextField(
            modifier = modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            paddingValues = paddingValues,
            placeholderText = placeholderText,
            leadingIcon = leadingIcon,
            supportingText = supportingText,
            enabled = enabled,
            singleLine = singleLine,
            isError = isError,
            title = title,
            readOnly = true,
            minLines = minLines,
            trailingIcon = { ExposedDropdownMenu(expanded) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = !expanded }
        ) {
            options?.forEach { value ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = value,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = { }
                )
            }
        }
    }
}

@Composable
internal fun ExposedDropdownMenu(expanded: Boolean) {
    val rotate: Float by animateFloatAsState(if (expanded) 180f else 0f)
    Icon(
        painter = painterResource(Res.drawable.ic_chevrone_bottom),
        contentDescription = stringResource(Res.string.cd_menu),
        modifier = Modifier.rotate(rotate),
        tint = MaterialTheme.colorScheme.tertiary
    )
}