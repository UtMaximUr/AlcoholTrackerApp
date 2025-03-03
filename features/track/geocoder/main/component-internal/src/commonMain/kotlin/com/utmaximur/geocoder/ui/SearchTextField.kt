package com.utmaximur.geocoder.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.utmaximur.design.text.InnerShadowTextField
import com.utmaximur.design.text.TextOutlinedLabel
import com.utmaximur.domain.models.Place

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchTextField(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    title: String,
    textValue: Any?,
    foundValues: List<Place>,
    searchIndicatorActive: Boolean,
    placeholderText: String = title,
    onValueChange: (String) -> Unit,
    onValueSelect: (Place) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val omItemClick: (Place) -> Unit = { value ->
        focusManager.clearFocus()
        keyboardController?.hide()
        expanded = !expanded
        onValueSelect(value)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded })
    {
        InnerShadowTextField(
            modifier = modifier.menuAnchor(MenuAnchorType.PrimaryEditable),
            paddingValues = paddingValues,
            supportingText = { TextOutlinedLabel(placeholderText) },
            title = title,
            textValue = textValue,
            onValueChange = onValueChange,
            trailingIcon = {
                AnimatedVisibility(
                    visible = searchIndicatorActive,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.tertiary,
                        strokeWidth = 2.dp
                    )
                }
            }
        )
        ExposedDropdownMenu(
            expanded = expanded && foundValues.isNotEmpty(),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            onDismissRequest = { expanded = !expanded }
        ) {
            foundValues.forEach { value ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = value.title,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = { omItemClick(value) }
                )
            }
        }
    }
}