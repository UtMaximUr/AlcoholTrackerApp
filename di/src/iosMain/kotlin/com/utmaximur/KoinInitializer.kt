package com.utmaximur

import org.koin.core.context.startKoin
import org.koin.ksp.generated.module


/**
 * Отвечает за инициализацию Koin.
 */
internal class KoinInitializer {
    init {
        startKoin {
            modules(SharedModule().module)
        }
    }
}