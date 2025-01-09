package com.utmaximur.design.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import design.resources.Res
import design.resources.without_value
import org.jetbrains.compose.resources.StringResource

@Composable
fun Map<String, StringResource>.getStringResource(
    idResource: String,
    placeholder: StringResource = Res.string.without_value
) = this[idResource] ?: placeholder

@Composable
fun TextOutlinedLabel(title: String) = Text(
    text = title,
    style = MaterialTheme.typography.labelMedium
)

@Composable
fun DefaultOutlinedTextFieldDefaults() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    disabledContainerColor = Color.Transparent,
    disabledBorderColor = Color.Transparent,
    errorContainerColor = Color.Transparent,
    errorBorderColor = MaterialTheme.colorScheme.tertiary,
    cursorColor = MaterialTheme.colorScheme.primary,
    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
    unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
    focusedLabelColor = MaterialTheme.colorScheme.tertiary
)