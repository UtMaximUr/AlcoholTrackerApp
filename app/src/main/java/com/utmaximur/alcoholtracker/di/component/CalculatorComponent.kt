package com.utmaximur.alcoholtracker.di.component

import com.utmaximur.alcoholtracker.di.module.CalculatorModule
import com.utmaximur.alcoholtracker.di.scope.CalculatorScope
import com.utmaximur.alcoholtracker.presentation.calculator.CalculatorViewModel
import dagger.Component

@Component(
    modules = [CalculatorModule::class]
)
@CalculatorScope
interface CalculatorComponent {

    @Component.Builder
    interface Builder {
        fun build(): CalculatorComponent
    }

    fun getViewModel() : CalculatorViewModel
}