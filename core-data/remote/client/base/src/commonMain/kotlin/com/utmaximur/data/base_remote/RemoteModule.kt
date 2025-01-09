package com.utmaximur.data.base_remote

import com.utmaximur.client.BuildKonfig
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import com.utmaximur.client.api.HttpClientProvider
import com.utmaximur.client.api.KtorfitProvider

@Module
@ComponentScan
class RemoteModule {
    @Single
    fun provideHttpClient(httpClientProvider: HttpClientProvider): HttpClient = httpClientProvider.build()

    @OptIn(ExperimentalSerializationApi::class)
    @Single
    fun provideJson(): Json = Json {
        explicitNulls = false
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Factory
    fun provideInitialBaseUrl() = BuildKonfig.BASE_URL

    @Single
    fun provideKtorfit(ktorfitProvider: KtorfitProvider): Ktorfit = ktorfitProvider.build()
}