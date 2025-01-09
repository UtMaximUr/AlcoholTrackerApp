package com.utmaximur.data.base_remote

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.android.Android
import org.koin.core.annotation.Single

@Single
actual fun provideHttpClientEngineFactory(): HttpClientEngineFactory<*> {
    return Android
}