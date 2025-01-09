package com.utmaximur.mappers.implementation

import com.utmaximur.core.mvi_mapper.Loading

sealed class LoadStateType : Loading {

    /**
     * Отсутствие загрузки в плейсхолдере, плейсхолдер скрыт
     */
    data object None : LoadStateType() {
        override val isLoading: Boolean = false
    }

    /**
     * Стейт активного PTR
     */
    data object SwipeRefreshLoading : LoadStateType() {
        override val isLoading: Boolean = true
    }

    /**
     * Контейнер с ошибкой и возможностью перезагрузить данные.
     */
    data object Error : LoadStateType() {
        override val isLoading: Boolean = false
    }

    /**
     * Контейнер с ошибкой об отсутствии интернета и возможностью перезагрузить данные.
     */
    data object NoInternet : LoadStateType() {
        override val isLoading: Boolean = false
    }

    /**
     * Контейнер с сообщением о том, что мы получили пустой список данных
     */
    data object Empty : LoadStateType() {
        override val isLoading: Boolean = false
    }

    /**
     * Полноэкранный лоадер, отображается поверх контента
     */
    data object Main : LoadStateType() {
        override val isLoading: Boolean = true
    }

    /**
     * Прозрачный лоадер, за которым видно контент.
     */
    data object TransparentLoading : LoadStateType() {
        override val isLoading: Boolean = true
    }
}