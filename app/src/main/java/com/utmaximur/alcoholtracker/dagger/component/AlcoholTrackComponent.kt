package com.utmaximur.alcoholtracker.dagger.component

import com.utmaximur.alcoholtracker.dagger.module.AlcoholTrackModule
import com.utmaximur.alcoholtracker.dagger.module.RoomDatabaseModule
import com.utmaximur.alcoholtracker.repository.DrinkRepository
import com.utmaximur.alcoholtracker.repository.TrackRepository
import com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.view.impl.AddFragment
import com.utmaximur.alcoholtracker.ui.calendar.presentation.view.impl.CalendarFragment
import com.utmaximur.alcoholtracker.ui.statistic.presentation.view.StatisticFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomDatabaseModule::class, AlcoholTrackModule::class])
interface AlcoholTrackComponent {

    fun provideTrackRepository(): TrackRepository

    fun provideDrinkRepository(): DrinkRepository

    fun inject(calendarFragment: CalendarFragment)

    fun inject(addFragment: AddFragment)

    fun inject(statisticFragment: StatisticFragment)
}