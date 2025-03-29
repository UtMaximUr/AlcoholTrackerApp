package com.utmaximur.data.kandinsky.network

object ApiConstants {
    const val HEADER_KEY = "X-Key"
    const val HEADER_SECRET = "X-Secret"
    const val BASE_URL = "https://api-key.fusionbrain.ai/"
    const val GET_STYLES_URL = "https://cdn.fusionbrain.ai/static/styles/key"
    const val GET_MODELS_ENDPOINT = "key/api/v1/models"
    const val GENERATION_REQUEST_ENDPOINT = "key/api/v1/text2image/run"
    const val CHECK_GENERATION_RESULT_ENDPOINT = "key/api/v1/text2image/status/{id}"
    const val REQUEST_PARAMETER_TYPE_GENERATE = "GENERATE"
    const val IMAGE_SIZE = 1024
    const val IMAGES_NUMBER = 1
    const val MODEL_ID = "model_id"
    const val PARAMS = "params"
    const val ID = "id"
}