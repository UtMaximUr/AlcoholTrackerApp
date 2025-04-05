package com.utmaximur

import android.content.Context
import androidx.startup.Initializer
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import com.utmaximur.data.kandinsky.generate_image.worker.WorkerInitializer

@Suppress("unused")
internal class WorkersInitializer : Initializer<WorkerInitializer>, KoinComponent {
    override fun create(context: Context): WorkerInitializer {
        println("created WorkersInitializer")
        val workerInitializer = get<WorkerInitializer>()
        workerInitializer.startProcess()
        return workerInitializer
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(KoinApplicationInitializer::class.java)

}