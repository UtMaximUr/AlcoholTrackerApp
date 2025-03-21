package com.utmaximur.data.geocoder.network

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

interface GeocoderApi {
    @GET(ApiConstants.BASE_URL)
    suspend fun getPlace(
        @Query(ApiConstants.TEXT) query: String,
        @Query(ApiConstants.APIKEY) apikey: String,
        @Query(ApiConstants.LANG) lang: String,
    ): FeatureCollection
}
