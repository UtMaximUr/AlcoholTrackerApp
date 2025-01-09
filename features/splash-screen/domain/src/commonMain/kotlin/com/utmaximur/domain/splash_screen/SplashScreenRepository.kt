package com.utmaximur.domain.splash_screen

interface SplashScreenRepository {

    suspend fun fetchAppData()
}