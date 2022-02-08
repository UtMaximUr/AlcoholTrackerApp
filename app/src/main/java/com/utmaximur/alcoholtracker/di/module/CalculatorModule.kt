package com.utmaximur.alcoholtracker.di.module

import com.utmaximur.alcoholtracker.di.scope.CalculatorScope
import com.utmaximur.alcoholtracker.presentation.calculator.CalculatorViewModel
import dagger.Module
import dagger.Provides


@Module
class CalculatorModule {

    @Provides
    @CalculatorScope
    fun provideViewModel(): CalculatorViewModel = CalculatorViewModel()

}