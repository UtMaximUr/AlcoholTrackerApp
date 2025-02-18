package com.utmaximur.core.mvi_mapper

/**
 * Данные для отображения на Ui асинхронного запроса на получение данных.
 *
 * В отличие от Request, содержащего только состояние запроса в текущий момент времени
 * (либо загрузка, либо данные, либо ошибка),
 * этот класс содержит комбинацию из них
 * (загрузка + данные + ошибка),
 *
 * Кроме того, содержит информацию о том, как именно состояние загрузки данных
 * должно быть отображено на UI: [Loading]
 *
 * @param data  данные, либо их отсутствие
 * @param load  обертка над состоянием загрузки, интерпретируемая на ui
 * @param error состояние ошибки
 */
data class RequestUi<T>(
    val data: T? = null,
    val load: Loading? = null,
    val error: Throwable? = null
) {

    /**
     * Флаг, определяющий, выполняется ли запрос в данный момент.
     * */
    val isLoading: Boolean get() = load?.isLoading ?: false

    /**
     * Флаг, определяющий, есть ли данные в результате выполнения запроса.
     * */
    val hasData: Boolean get() = data != null

    /**
     * Флаг, определяющий, есть ли ошибка в результате выполнения запроса.
     * */
    val hasError: Boolean get() = error != null

    /**
     * Флаг, определяющий пустой [RequestUi]
     */
    val isEmpty: Boolean get() = data == null && load == null && error == null
}