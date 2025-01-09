package com.utmaximur.client.api

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.builtin.FlowConverterFactory
import io.ktor.client.HttpClient

abstract class BaseKtorfit(
    private val client: HttpClient,
    initialBaseUrl: String
) : KtorfitProvider {

    private var baseUrl: String? = initialBaseUrl
    override fun provideBaseUrl(url: String) {
        baseUrl = url
    }

    override fun build(): Ktorfit {
        return Ktorfit.Builder()
            .baseUrl(baseUrl ?: throw Exception("don't provided BASE_URL"))
            .httpClient(client)
            .converterFactories(FlowConverterFactory())
            .build()
    }
}