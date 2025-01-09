@file:Suppress("UNRESOLVED_REFERENCE")

package com.utmaximur

import org.koin.core.context.startKoin

/**
 * Отвечает за инициализацию Koin.
 */
internal class KoinInitializer {

    init {
        startKoin {
            modules(SharedModule().module)
        }.koin
    }
}