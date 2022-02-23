package com.utmaximur.data.di

import com.utmaximur.data.assets.AssetsModule
import com.utmaximur.data.assets.mapper.IconMapper
import com.utmaximur.data.file.FileManager
import com.utmaximur.data.local_data_source.AlcoholTrackDatabase
import com.utmaximur.data.local_data_source.mapper.DrinkMapper
import com.utmaximur.data.local_data_source.mapper.TrackMapper
import com.utmaximur.data.preferences.SharedPref
import com.utmaximur.data.repository.*
import com.utmaximur.data.update.UpdateManager
import com.utmaximur.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesTrackRepository(
        alcoholTrackDatabase: AlcoholTrackDatabase,
        trackMapper: TrackMapper
    ): TrackRepository {
        return TrackRepositoryImpl(alcoholTrackDatabase, trackMapper)
    }

    @Singleton
    @Provides
    fun providesDrinkRepository(
        alcoholTrackDatabase: AlcoholTrackDatabase,
        drinkMapper: DrinkMapper
    ): DrinkRepository {
        return DrinkRepositoryImpl(alcoholTrackDatabase, drinkMapper)
    }

    @Singleton
    @Provides
    fun providesAssetsRepository(
        assetsModule: AssetsModule,
        drinkMapper: DrinkMapper,
        iconMapper: IconMapper
    ): AssetsRepository {
        return AssetsRepositoryImpl(assetsModule, iconMapper, drinkMapper)
    }

    @Singleton
    @Provides
    fun providesFileRepository(fileManager: FileManager): FileRepository {
        return FileRepositoryImpl(fileManager)
    }

    @Singleton
    @Provides
    fun providesPreferencesRepository(sharedPreferences: SharedPref): PreferencesRepository {
        return PreferencesRepositoryImpl(sharedPreferences)
    }

    @Singleton
    @Provides
    fun providesUpdateRepository(updateManager: UpdateManager): UpdateRepository {
        return UpdateRepositoryImpl(updateManager)
    }
}