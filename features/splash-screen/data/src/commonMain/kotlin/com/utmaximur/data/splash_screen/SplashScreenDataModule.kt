package com.utmaximur.data.splash_screen

import com.utmaximur.data.splash_screen.network.SplashScreenApi
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single


@Module
@ComponentScan
class SplashScreenDataModule {
    @Single
    fun provideSplashScreenApi(ktorfit: Ktorfit): SplashScreenApi = ktorfit.create()
}