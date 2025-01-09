package com.utmaximur.calculator

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [CalculatorDomainMainModule::class])
@ComponentScan
class CalculatorMainComponentModule