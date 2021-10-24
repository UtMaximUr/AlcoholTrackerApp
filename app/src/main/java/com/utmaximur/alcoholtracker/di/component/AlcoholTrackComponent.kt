package com.utmaximur.alcoholtracker.di.component

import com.utmaximur.alcoholtracker.di.module.AlcoholTrackModule
import com.utmaximur.alcoholtracker.di.module.RoomDatabaseModule
import com.utmaximur.alcoholtracker.data.repository.DrinkRepository
import com.utmaximur.alcoholtracker.data.repository.FileRepository
import com.utmaximur.alcoholtracker.data.repository.AssetsRepository
import com.utmaximur.alcoholtracker.data.repository.TrackRepository
import com.utmaximur.alcoholtracker.presentation.create_track.AddFragment
import com.utmaximur.alcoholtracker.presentation.create_my_drink.AddNewDrink
import com.utmaximur.alcoholtracker.presentation.calculator.CalculatorFragment
import com.utmaximur.alcoholtracker.presentation.calendar.CalendarFragment
import com.utmaximur.alcoholtracker.presentation.dialog.add_photo.AddPhotoBottomDialogFragment
import com.utmaximur.alcoholtracker.presentation.calendar.TrackListBottomDialog
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

    fun inject(calendarFragment: CalendarFragment)

    fun inject(addFragment: AddFragment)

    fun inject(addNewDrink: AddNewDrink)

    fun inject(addPhotoBottomDialogFragment: AddPhotoBottomDialogFragment)

    fun inject(calculatorFragment: CalculatorFragment)

    fun inject(statisticFragment: StatisticFragment)

    fun inject(trackListBottomDialogFragment: TrackListBottomDialog)
}