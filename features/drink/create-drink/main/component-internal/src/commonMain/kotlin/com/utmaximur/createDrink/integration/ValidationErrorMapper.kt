package com.utmaximur.createDrink.integration

import com.utmaximur.createDrink.validation.ValidationError
import features.drink.create_drink.main.Res
import features.drink.create_drink.main.icon_url_empty
import features.drink.create_drink.main.name_empty
import features.drink.create_drink.main.photo_url_empty
import org.jetbrains.compose.resources.getString

/**
 * Преобразует ошибку валидации в локализованное сообщение для отображения пользователю.
 *
 * @receiver Ошибка валидации из доменного слоя
 * @return Готовое к отображению сообщение, полученное из ресурсов
 */
internal suspend fun ValidationError.toLocalizedMessage() = when (this) {
    ValidationError.IconUrlEmpty -> getString(Res.string.icon_url_empty)
    ValidationError.NameEmpty -> getString(Res.string.name_empty)
    ValidationError.PhotoUrlEmpty -> getString(Res.string.photo_url_empty)
}