package com.utmaximur.alcoholtracker.di.component

import com.utmaximur.alcoholtracker.di.module.AlcoholTrackModule
import com.utmaximur.alcoholtracker.di.module.RoomDatabaseModule
import com.utmaximur.alcoholtracker.data.repository.DrinkRepository
import com.utmaximur.alcoholtracker.data.repository.FileRepository
import com.utmaximur.alcoholtracker.data.repository.AssetsRepository
import com.utmaximur.alcoholtracker.data.repository.TrackRepository
import com.utmaximur.alcoholtracker.presantation.add.AddFragment
import com.utmaximur.alcoholtracker.presantation.addmydrink.AddNewDrink
import com.utmaximur.alcoholtracker.presantation.calculator.CalculatorFragment
import com.utmaximur.alcoholtracker.presantation.calendar.CalendarFragment
import com.utmaximur.alcoholtracker.presantation.dialog.addphoto.AddPhotoBottomDialogFragment
import com.utmaximur.alcoholtracker.presantation.statistic.StatisticFragment
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
}