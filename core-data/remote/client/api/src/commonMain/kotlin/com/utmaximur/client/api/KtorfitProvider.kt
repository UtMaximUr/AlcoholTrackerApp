package com.utmaximur.client.api

import de.jensklingenberg.ktorfit.Ktorfit

interface KtorfitProvider {
    fun build(): Ktorfit
    fun provideBaseUrl(url: String)
}