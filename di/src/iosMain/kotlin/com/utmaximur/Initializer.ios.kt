package com.utmaximur

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parameterArrayOf
import com.utmaximur.root.RootComponent

/**
 * Класс `Initializer` отвечает за:
 * 1. Инициализацию и настройку Koin.
 * 2. Управление основными компонентами приложения, такими как `WorkManager` и `RootComponent`.
 *
 * @param lifecycle Указывает жизненный цикл приложения, который используется для создания `DefaultComponentContext`.
 */
class Initializer(
    lifecycle: Lifecycle
) : KoinComponent {

    /**
     * KoinInitializer - Инициализатор Koin для загрузки необходимых модулей.
     * WorkersInitializer - Инициализатор WorkManager.
     */
    init {
        KoinInitializer()
        WorkersInitializer()
    }

    /**
     * Корневой компонент приложения, отвечающий за связывание контекста жизненного цикла.
     */
    val rootComponent: RootComponent = get {
        parameterArrayOf(
            DefaultComponentContext(lifecycle = lifecycle)
        )
    }
}