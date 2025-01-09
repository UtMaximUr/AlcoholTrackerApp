package com.utmaximur.data.base_remote

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.engine.darwin.DarwinHttpRequestException
import org.koin.core.annotation.Single
import com.utmaximur.remote.errors.ThrowableToNSErrorMapper
@Single
actual fun provideHttpClientEngineFactory(): HttpClientEngineFactory<*> {
    ThrowableToNSErrorMapper.setup { (it as? DarwinHttpRequestException)?.origin }
    return Darwin
}