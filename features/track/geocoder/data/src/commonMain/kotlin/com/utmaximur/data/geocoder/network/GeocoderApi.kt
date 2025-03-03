package com.utmaximur.data.geocoder.network

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

interface GeocoderApi {
    @GET("https://search-maps.yandex.ru/v1/")
    suspend fun getPlace(
        @Query("text") query: String,
        @Query("apikey") apikey: String,
        @Query("lang") lang: String
    ): FeatureCollection
}