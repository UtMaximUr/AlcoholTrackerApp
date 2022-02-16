package com.utmaximur.alcoholtracker.di.module

import android.content.Context
import android.content.SharedPreferences
import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.assets.AssetsModule
import com.utmaximur.alcoholtracker.data.file.FileManager
import com.utmaximur.alcoholtracker.data.mapper.DrinkMapper
import com.utmaximur.alcoholtracker.data.mapper.IconMapper
import com.utmaximur.alcoholtracker.data.mapper.TrackMapper
import com.utmaximur.alcoholtracker.data.preferences.SharedPref
import com.utmaximur.alcoholtracker.data.repository.*
import com.utmaximur.alcoholtracker.domain.interactor.*
import com.utmaximur.alcoholtracker.domain.mapper.TrackListMapper
import com.utmaximur.alcoholtracker.domain.mapper.StatisticsMapper
import com.utmaximur.alcoholtracker.util.PREFS_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideTrackMapper() = TrackMapper()

    @Provides
    fun provideDrinkMapper() = DrinkMapper()

    @Provides
    fun provideIconMapper() = IconMapper()

    @Provides
    fun provideCalendarMapper() = TrackListMapper()

    @Provides
    fun provideStatisticsMapper() = StatisticsMapper()

    @Provides
    fun provideAssets(@ApplicationContext context: Context): AssetsModule {
        return AssetsModule(context.assets)
    }

    @Provides
    fun provideFileManager(@ApplicationContext context: Context): FileManager {
        return FileManager(context)
    }


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

    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun providesSharedPref(sharedPreferences: SharedPreferences) = SharedPref(sharedPreferences)

    @Singleton
    @Provides
    fun providesPreferencesRepository(sharedPreferences: SharedPref): PreferencesRepository {
        return PreferencesRepository(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideAddNewDrinkInteractor(
        drinkRepository: DrinkRepository,
        assetsRepository: AssetsRepository
    ): AddNewDrinkInteractor = AddNewDrinkInteractor(drinkRepository, assetsRepository)

    @Provides
    @Singleton
    fun provideAddTrackInteractor(
        trackRepository: TrackRepository,
        drinkRepository: DrinkRepository
    ): AddTrackInteractor = AddTrackInteractor(trackRepository, drinkRepository)

    @Provides
    @Singleton
    fun provideCalendarInteractor(
        trackRepository: TrackRepository,
        trackListMapper: TrackListMapper
    ): CalendarInteractor = CalendarInteractor(trackRepository, trackListMapper)

    @Provides
    @Singleton
    fun provideSettingsInteractor(
        preferencesRepository: PreferencesRepository
    ): SettingsInteractor =
        SettingsInteractor(preferencesRepository)

    @Provides
    @Singleton
    fun provideStatisticInteractor(
        trackRepository: TrackRepository,
        drinkRepository: DrinkRepository,
        statisticsMapper: StatisticsMapper
    ): StatisticInteractor = StatisticInteractor(trackRepository, drinkRepository, statisticsMapper)
}