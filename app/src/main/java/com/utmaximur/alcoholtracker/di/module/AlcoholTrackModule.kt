package com.utmaximur.alcoholtracker.di.module

import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.assets.AssetsModule
import com.utmaximur.alcoholtracker.data.file.FileManager
import com.utmaximur.alcoholtracker.data.mapper.DrinkMapper
import com.utmaximur.alcoholtracker.data.mapper.IconMapper
import com.utmaximur.alcoholtracker.data.mapper.TrackMapper
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
    fun providesTrackRepository(
        alcoholTrackDatabase: AlcoholTrackDatabase,
        trackMapper: TrackMapper
    ): TrackRepository {
        return TrackRepository(alcoholTrackDatabase, trackMapper)
    }

    @Singleton
    @Provides
    fun providesDrinkRepository(
        alcoholTrackDatabase: AlcoholTrackDatabase,
        drinkMapper: DrinkMapper
    ): DrinkRepository {
        return DrinkRepository(alcoholTrackDatabase, drinkMapper)
    }

    @Singleton
    @Provides
    fun providesAssetsRepository(
        assetsModule: AssetsModule,
        drinkMapper: DrinkMapper,
        iconMapper: IconMapper
    ): AssetsRepository {
        return AssetsRepository(assetsModule, iconMapper, drinkMapper)
    }

    @Singleton
    @Provides
    fun providesFileRepository(fileManager: FileManager): FileRepository {
        return FileRepository(fileManager)
    }
}