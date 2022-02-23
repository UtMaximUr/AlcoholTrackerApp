package com.utmaximur.data.di

import android.content.Context
import com.utmaximur.data.assets.AssetsModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AssetsModule {
    @Singleton
    @Provides
    fun provideAssets(@ApplicationContext context: Context): AssetsModule {
        return AssetsModule(context.assets)
    }
}