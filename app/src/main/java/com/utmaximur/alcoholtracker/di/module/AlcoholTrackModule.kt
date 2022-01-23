package com.utmaximur.alcoholtracker.di.module

import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.assets.AssetsModule
import com.utmaximur.alcoholtracker.data.file.FileManager
import com.utmaximur.alcoholtracker.data.mapper.DrinkMapper
import com.utmaximur.alcoholtracker.data.mapper.IconMapper
import com.utmaximur.alcoholtracker.data.mapper.TrackMapper
import com.utmaximur.alcoholtracker.data.preferences.SharedPref
import com.utmaximur.alcoholtracker.data.repository.*
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

    @Singleton
    @Provides
    fun providesPreferencesRepository(sharedPreferences: SharedPref): PreferencesRepository {
        return PreferencesRepository(sharedPreferences)
    }
}