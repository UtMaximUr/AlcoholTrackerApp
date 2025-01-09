package com.utmaximur.data.base_remote

import io.ktor.client.engine.HttpClientEngineFactory

internal expect fun provideHttpClientEngineFactory(): HttpClientEngineFactory<*>