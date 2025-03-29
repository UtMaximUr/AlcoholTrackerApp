package com.utmaximur.data.kandinsky.generate_image.worker

import com.utmaximur.workmanager.dsl.simpleWorkMangerInitializer
import org.koin.core.annotation.Factory

interface WorkerInitializer {
    fun startProcess()
}

@Factory
internal class WorkersInitializationProvider(
    private val generateImageWorker: GenerateImageWorker
) : WorkerInitializer {
    override fun startProcess() {
        simpleWorkMangerInitializer {
            logger { enable = true }
            work(generateImageWorker)
        }
    }
}