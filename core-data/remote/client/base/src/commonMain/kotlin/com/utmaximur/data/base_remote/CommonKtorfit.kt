package com.utmaximur.data.base_remote

import io.ktor.client.HttpClient
import org.koin.core.annotation.Factory
import com.utmaximur.client.api.BaseKtorfit
import com.utmaximur.client.api.KtorfitProvider

@Factory(binds = [KtorfitProvider::class])
internal class CommonKtorfit(
    client: HttpClient,
    initialBaseUrl: String
) : BaseKtorfit(client, initialBaseUrl)