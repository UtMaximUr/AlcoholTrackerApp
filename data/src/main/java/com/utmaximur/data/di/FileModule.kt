package com.utmaximur.data.di

import android.content.Context
import com.utmaximur.data.file.FileManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FileModule {
    @Singleton
    @Provides
    fun provideFileManager(@ApplicationContext context: Context): FileManager {
        return FileManager(context)
    }
}