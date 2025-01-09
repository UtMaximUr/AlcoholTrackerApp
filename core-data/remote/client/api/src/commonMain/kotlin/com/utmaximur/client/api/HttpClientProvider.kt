package com.utmaximur.client.api

import io.ktor.client.HttpClient

interface HttpClientProvider {
    fun build(): HttpClient
}