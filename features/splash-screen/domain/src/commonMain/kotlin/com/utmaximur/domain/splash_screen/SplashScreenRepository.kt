package com.utmaximur.domain.splash_screen

interface SplashScreenRepository {

    suspend fun checkNotEmptyTable() : Boolean

    suspend fun fetchAppData() : Boolean
}