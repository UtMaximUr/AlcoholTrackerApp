package com.utmaximur.data.calculator

import com.github.murzagalin.evaluator.Evaluator
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module


@Module
@ComponentScan
class CalculatorDataModule

@Factory
internal fun provideEvaluator() = Evaluator()