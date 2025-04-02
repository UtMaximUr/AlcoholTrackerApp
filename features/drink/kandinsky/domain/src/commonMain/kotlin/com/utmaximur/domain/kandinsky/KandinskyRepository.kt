package com.utmaximur.domain.kandinsky

import kotlinx.coroutines.flow.Flow

interface KandinskyRepository {

    suspend fun getStyles(): List<ImageStyle>

    fun observeGenerationResult(): Flow<GenerationResult>

    suspend fun requestGeneration(prompt: String, styleName: String)

    suspend fun cancelAllGenerations()
}