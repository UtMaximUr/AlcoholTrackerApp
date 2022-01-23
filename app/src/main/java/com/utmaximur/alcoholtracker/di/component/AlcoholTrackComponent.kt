package com.utmaximur.alcoholtracker.di.component

import com.utmaximur.alcoholtracker.data.repository.*
import com.utmaximur.alcoholtracker.di.module.AlcoholTrackModule
import com.utmaximur.alcoholtracker.di.module.RoomDatabaseModule
import com.utmaximur.alcoholtracker.presentation.create_track.CreateTrackFragment
import com.utmaximur.alcoholtracker.presentation.create_my_drink.CreateMyDrink
import com.utmaximur.alcoholtracker.presentation.calculator.CalculatorFragment
import com.utmaximur.alcoholtracker.presentation.calendar.CalendarFragment
import com.utmaximur.alcoholtracker.presentation.dialog.add_photo.AddPhotoBottomDialogFragment
import com.utmaximur.alcoholtracker.presentation.calendar.TrackListBottomDialog
import com.utmaximur.alcoholtracker.presentation.settings.SettingsFragment
import com.utmaximur.alcoholtracker.presentation.statistic.StatisticFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomDatabaseModule::class, AlcoholTrackModule::class])
interface AlcoholTrackComponent {

    fun provideTrackRepository(): TrackRepository

    fun provideDrinkRepository(): DrinkRepository

    fun provideAssetsRepository(): AssetsRepository

    fun provideFileRepository(): FileRepository

    fun providePreferencesRepository(): PreferencesRepository

    fun inject(calendarFragment: CalendarFragment)

    fun inject(createTrackFragment: CreateTrackFragment)

    fun inject(createMyDrink: CreateMyDrink)

    fun inject(addPhotoBottomDialogFragment: AddPhotoBottomDialogFragment)

    fun inject(calculatorFragment: CalculatorFragment)

    fun inject(statisticFragment: StatisticFragment)

    fun inject(trackListBottomDialogFragment: TrackListBottomDialog)

    fun inject(settingsFragment: SettingsFragment)

}