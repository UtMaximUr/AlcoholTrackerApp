package com.utmaximur.data.base_remote

import com.utmaximur.app.base.app.ApplicationInfo
import com.utmaximur.app.base.app.Flavor
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory
import com.utmaximur.client.api.HttpClientProvider
import com.utmaximur.remote.errors.NetworkResponseError

@Factory
internal class BaseClient(
    private val engineFactory: HttpClientEngineFactory<*>,
    private val applicationInfo: ApplicationInfo,
    private val json: Json
) : HttpClientProvider {

    override fun build(): HttpClient {
        return HttpClient(engineFactory) {
            install(HttpTimeout) {
                connectTimeoutMillis = CONNECT_TIMEOUT_MILLISECONDS
                requestTimeoutMillis = READ_WRITE_TIMEOUT_MILLISECONDS
            }
            if (applicationInfo.flavor == Flavor.Qa) {
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }
            }
            install(ContentNegotiation) {
                json(json)
                json(contentType = ContentType.Text.Plain)
            }
            HttpResponseValidator {
                validateResponse { response ->
                    // check only bed response code
                    NetworkResponseError.create(response.status)?.let { throw it }
                }
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }

    companion object {
        private const val CONNECT_TIMEOUT_MILLISECONDS = 30000L
        private const val READ_WRITE_TIMEOUT_MILLISECONDS = 60000L
    }
}