package com.utmaximur.domain.kandinsky

import kotlinx.coroutines.flow.Flow

interface KandinskyRepository {

    fun getStyles(): Flow<List<ImageStyle>>

    fun observeGenerationResult(): Flow<GenerationResult>

    suspend fun requestGeneration(prompt: String, styleName: String)

    suspend fun cancelAllGenerations()
}