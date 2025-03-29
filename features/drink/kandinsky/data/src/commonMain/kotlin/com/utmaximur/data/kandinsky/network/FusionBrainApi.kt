package com.utmaximur.data.kandinsky.network

import com.utmaximur.data.kandinsky.network.models.FusionBrainVersion
import com.utmaximur.data.kandinsky.network.models.GenerateResult
import com.utmaximur.data.kandinsky.network.models.ImageStyleRemote
import com.utmaximur.kandisky.BuildKonfig
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import io.ktor.client.request.forms.MultiPartFormDataContent
import kotlinx.coroutines.flow.Flow

interface FusionBrainApi {

    @GET(ApiConstants.GET_STYLES_URL)
    fun getStyles(): Flow<List<ImageStyleRemote>>

    @GET(ApiConstants.BASE_URL + ApiConstants.GET_MODELS_ENDPOINT)
    suspend fun getModels(
        @Header(ApiConstants.HEADER_KEY) key: String = apiKey,
        @Header(ApiConstants.HEADER_SECRET) secret: String = secretKey,
    ): List<FusionBrainVersion>

    @POST(ApiConstants.BASE_URL + ApiConstants.GENERATION_REQUEST_ENDPOINT)
    suspend fun postGenerateImageRequest(
        @Header(ApiConstants.HEADER_KEY) key: String = apiKey,
        @Header(ApiConstants.HEADER_SECRET) secret: String = secretKey,
        @Body body: MultiPartFormDataContent
    ): GenerateResult

    @GET(ApiConstants.BASE_URL + ApiConstants.CHECK_GENERATION_RESULT_ENDPOINT)
    suspend fun getRequestStatusOrImage(
        @Header(ApiConstants.HEADER_KEY) key: String = apiKey,
        @Header(ApiConstants.HEADER_SECRET) secret: String = secretKey,
        @Path(ApiConstants.ID) id: String
    ): GenerateResult

    companion object {
        private val apiKey = BuildKonfig.KANDINSKY_API_KEY
        private val secretKey = BuildKonfig.KANDINSKY_SECRET_KEY
    }
}