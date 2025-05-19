package com.utmaximur.kandinsky.integration

import com.utmaximur.kandinsky.validation.ValidationError
import features.drink.kandinsky.main.Res
import features.drink.kandinsky.main.prompt_empty
import features.drink.kandinsky.main.style_empty
import org.jetbrains.compose.resources.getString

/**
 * Преобразует ошибку валидации в локализованное сообщение для отображения пользователю.
 *
 * @receiver Ошибка валидации из доменного слоя
 * @return Готовое к отображению сообщение, полученное из ресурсов
 */
internal suspend fun ValidationError.toLocalizedMessage() = when (this) {
    ValidationError.PromptEmpty -> getString(Res.string.prompt_empty)
    ValidationError.StyleEmpty -> getString(Res.string.style_empty)
}