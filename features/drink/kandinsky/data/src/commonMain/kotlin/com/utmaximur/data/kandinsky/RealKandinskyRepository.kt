package com.utmaximur.data.kandinsky

import com.utmaximur.data.kandinsky.generate_image.GenerateResultDataSource
import com.utmaximur.data.kandinsky.generate_image.worker.GenerateImageWorker
import com.utmaximur.data.kandinsky.mapper.MapperHolder
import com.utmaximur.data.kandinsky.network.FusionBrainApi
import com.utmaximur.domain.kandinsky.GenerationResult
import com.utmaximur.domain.kandinsky.ImageStyle
import com.utmaximur.domain.kandinsky.KandinskyRepository
import com.utmaximur.utils.extensions.mapList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
internal class RealKandinskyRepository(
    private val fusionBrainApi: FusionBrainApi,
    private val generateResultDataSource: GenerateResultDataSource,
    private val mapperHolder: MapperHolder
) : KandinskyRepository {

    override fun getStyles(): Flow<List<ImageStyle>> = fusionBrainApi.getStyles()
        .mapList(mapperHolder.imageStyleUiMapper::transform)

    override suspend fun requestGeneration(prompt: String, styleName: String) =
        GenerateImageWorker.sendRequestGeneration(prompt = prompt, styleName = styleName)

    override fun observeGenerationResult(): Flow<GenerationResult> =
        generateResultDataSource.dataFlow.map(mapperHolder.generationResultUiMapper::transform)

    override suspend fun cancelAllGenerations() = GenerateImageWorker.cancelAllWork()
}