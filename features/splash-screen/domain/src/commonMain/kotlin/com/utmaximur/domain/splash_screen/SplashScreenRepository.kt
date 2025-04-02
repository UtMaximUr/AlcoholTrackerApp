package com.utmaximur.domain.splash_screen

interface SplashScreenRepository {

    suspend fun hasAllEssentialData() : Boolean

    suspend fun fetchAppData() : Boolean
}