package com.utmaximur.data.splash_screen.network

import de.jensklingenberg.ktorfit.http.GET

interface SplashScreenApi {
    @GET("drinks.json")
    suspend fun getDrinks(): List<DrinkRemote>

    @GET("icons.json")
    suspend fun getIcons(): List<IconRemote>
}