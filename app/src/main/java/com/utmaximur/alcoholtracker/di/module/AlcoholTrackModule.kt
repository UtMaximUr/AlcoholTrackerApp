package com.utmaximur.alcoholtracker.di.module

import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.assets.AssetsModule
import com.utmaximur.alcoholtracker.data.file.FileManager
import com.utmaximur.alcoholtracker.data.repository.DrinkRepository
import com.utmaximur.alcoholtracker.data.repository.FileRepository
import com.utmaximur.alcoholtracker.data.repository.AssetsRepository
import com.utmaximur.alcoholtracker.data.repository.TrackRepository
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
    fun providesAssetsRepository(assetsModule: AssetsModule): AssetsRepository {
        return AssetsRepository(assetsModule)
    }

    @Singleton
    @Provides
    fun providesFileRepository(fileManager: FileManager): FileRepository {
        return FileRepository(fileManager)
    }
}