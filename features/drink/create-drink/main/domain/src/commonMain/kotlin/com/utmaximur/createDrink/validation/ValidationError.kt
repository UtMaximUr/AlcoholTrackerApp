package com.utmaximur.createDrink.validation

/**
 * Иерархия ошибок валидации, используемая в доменном слое.
 *
 * Представляет ошибки, связанные с проверкой пользовательского ввода
 * или бизнес-правил приложения.
 */
sealed interface ValidationError {
    /**
     * Ошибка: отсутствует ссылка на основное изображение.
     *
     * Возникает, когда поле `photoUrl`:
     * - Содержит пустую строку
     */
    data object PhotoUrlEmpty : ValidationError

    /**
     * Ошибка: не указано название сущности.
     */
    data object NameEmpty : ValidationError

    /**
     * Ошибка: отсутствует иконка-превью.
     */
    data object IconUrlEmpty : ValidationError
}
