package com.utmaximur.design.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.utmaximur.design.extensions.clearTags
import com.utmaximur.design.extensions.innerShadow

@Composable
fun InnerShadowTextField(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    title: String? = null,
    textValue: Any? = null,
    placeholderText: String = title.orEmpty(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {},
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    isError: Boolean = false,
    minLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    val message = remember(key1 = textValue) {
        mutableStateOf(textValue?.toString().orEmpty())
    }.also { onValueChange(it.value) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        title?.let { TextOutlinedLabel(it) }
        OutlinedTextField(
            modifier = modifier
                .innerShadow()
                .fillMaxWidth(),
            enabled = enabled,
            value = message.value.clearTags(),
            onValueChange = { text -> message.value = text },
            readOnly = readOnly,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = keyboardType
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            colors = DefaultOutlinedTextFieldDefaults(),
            singleLine = singleLine,
            shape = MaterialTheme.shapes.large,
            placeholder = {
                Text(
                    text = placeholderText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            },
            minLines = minLines,
            isError = isError
        )
        supportingText?.let { it() }
    }
}