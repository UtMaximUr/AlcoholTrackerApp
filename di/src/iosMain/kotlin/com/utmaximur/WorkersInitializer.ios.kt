package com.utmaximur

import com.utmaximur.data.kandinsky.generate_image.worker.WorkerInitializer
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal class WorkersInitializer : KoinComponent {
    init {
        val workerInitializer = get<WorkerInitializer>()
        workerInitializer.startProcess()
    }
}