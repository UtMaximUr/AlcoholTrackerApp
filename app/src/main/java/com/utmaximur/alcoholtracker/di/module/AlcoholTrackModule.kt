package com.utmaximur.alcoholtracker.di.module

import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.assets.AssetsModule
import com.utmaximur.alcoholtracker.data.resources.IconRaw
import com.utmaximur.alcoholtracker.data.file.FileManager
import com.utmaximur.alcoholtracker.repository.DrinkRepository
import com.utmaximur.alcoholtracker.repository.FileRepository
import com.utmaximur.alcoholtracker.repository.IconRepository
import com.utmaximur.alcoholtracker.repository.TrackRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AlcoholTrackModule {

    @Singleton
    @Provides
    fun providesTrackRepository(alcoholTrackDatabase: AlcoholTrackDatabase): TrackRepository {
        return TrackRepository(alcoholTrackDatabase)
    }

    @Singleton
    @Provides
    fun providesDrinkRepository(alcoholTrackDatabase: AlcoholTrackDatabase): DrinkRepository {
        return DrinkRepository(alcoholTrackDatabase)
    }

    @Singleton
    @Provides
    fun providesIconRepository(assetsModule: AssetsModule): IconRepository {
        return IconRepository(assetsModule)
    }

    @Singleton
    @Provides
    fun providesFileRepository(fileManager: FileManager): FileRepository {
        return FileRepository(fileManager)
    }
}