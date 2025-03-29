package com.utmaximur.kandinsky.interactor

import com.utmaximur.domain.Interactor
import com.utmaximur.domain.kandinsky.KandinskyRepository
import com.utmaximur.kandinsky.GenerateImageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
internal class GenerateImage(
    createDrinkRepository: Lazy<KandinskyRepository>
) : Interactor<GenerateImageData, Unit>() {

    private val repository by createDrinkRepository

    override suspend fun doWork(params: GenerateImageData) {
        withContext(Dispatchers.IO) {
            repository.requestGeneration(params.prompt, params.style)
        }
    }
}