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
import com.utmaximur.design.extensions.tapToCloseKeyboard
import com.utmaximur.design.ui.trailingOrBlockedIcon

/**
 * Компонент текстового поля с внутренней тенью, поддерживающий различные кастомизации.
 *
 * @param modifier Модификатор для настройки внешнего вида и поведения текстового поля.
 * @param paddingValues Отступы вокруг текстового поля. По умолчанию используются пустые отступы.
 * @param title Заголовок текстового поля. Если не указан, используется [placeholderText].
 * @param textValue Текущее значение текстового поля. Может быть любого типа, но будет преобразовано в строку.
 * @param placeholderText Текст-заполнитель, который отображается, когда поле пустое.
 * @param leadingIcon Иконка, отображаемая в начале текстового поля.
 * @param trailingIcon Иконка, отображаемая в конце текстового поля.
 * @param supportingText Дополнительный текст, отображаемый под полем (например, подсказка или сообщение об ошибке).
 * @param onValueChange Колбэк, вызываемый при изменении текста в поле.
 * @param enabled Включено ли текстовое поле. Если `false`, поле становится недоступным для редактирования.
 * @param readOnly Только для чтения. Если `true`, поле нельзя редактировать, но оно остается доступным для взаимодействия.
 * @param singleLine Ограничивает текст одной строкой. Если `true`, текст не переносится на новую строку.
 * @param isError Указывает, находится ли поле в состоянии ошибки. Если `true`, поле подсвечивается как ошибочное.
 * @param minLines Минимальное количество строк, которое может занимать текстовое поле.
 * @param keyboardType Тип клавиатуры, который будет отображаться при фокусе на поле (например, текст, число, email).
 *
 */
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
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    val message = remember(key1 = textValue) { mutableStateOf(textValue?.toString().orEmpty()) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .tapToCloseKeyboard()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        title?.let { TextOutlinedLabel(it) }
        OutlinedTextField(
            modifier = modifier
                .innerShadow()
                .fillMaxWidth(),
            enabled = enabled,
            value = message.value.clearTags(),
            onValueChange = { text ->
                message.value = text
                onValueChange(text)
            },
            readOnly = readOnly,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = keyboardType,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                },
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            leadingIcon = leadingIcon,
            trailingIcon = trailingOrBlockedIcon(enabled, trailingIcon),
            colors = DefaultOutlinedTextFieldDefaults(),
            singleLine = singleLine,
            shape = MaterialTheme.shapes.large,
            placeholder = {
                Text(
                    text = placeholderText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )
            },
            minLines = minLines,
            isError = isError,
        )
        supportingText?.let { it() }
    }
}