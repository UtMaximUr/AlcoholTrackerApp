package com.utmaximur.alcoholtracker.dagger.component

import com.utmaximur.alcoholtracker.dagger.module.AlcoholTrackModule
import com.utmaximur.alcoholtracker.dagger.module.RoomDatabaseModule
import com.utmaximur.alcoholtracker.repository.DrinkRepository
import com.utmaximur.alcoholtracker.repository.FileRepository
import com.utmaximur.alcoholtracker.repository.IconRepository
import com.utmaximur.alcoholtracker.repository.TrackRepository
import com.utmaximur.alcoholtracker.ui.add.AddFragment
import com.utmaximur.alcoholtracker.ui.addmydrink.AddNewDrink
import com.utmaximur.alcoholtracker.ui.calculator.CalculatorFragment
import com.utmaximur.alcoholtracker.ui.calendar.CalendarFragment
import com.utmaximur.alcoholtracker.ui.statistic.StatisticFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomDatabaseModule::class, AlcoholTrackModule::class])
interface AlcoholTrackComponent {

    fun provideTrackRepository(): TrackRepository

    fun provideDrinkRepository(): DrinkRepository

    fun provideIconRepository(): IconRepository

    fun provideFileRepository(): FileRepository

    fun inject(calendarFragment: CalendarFragment)

    fun inject(addFragment: AddFragment)

    fun inject(addNewDrink: AddNewDrink)

    fun inject(calculatorFragment: CalculatorFragment)

    fun inject(statisticFragment: StatisticFragment)
}