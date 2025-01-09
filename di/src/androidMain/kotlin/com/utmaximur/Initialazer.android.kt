package com.utmaximur

import android.content.Context
import androidx.startup.Initializer
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

internal class KoinApplicationInitializer : Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication = startKoin {
        println("created KoinApplicationInitializer")
        androidContext(context.applicationContext)
        modules(SharedModule().module)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

}