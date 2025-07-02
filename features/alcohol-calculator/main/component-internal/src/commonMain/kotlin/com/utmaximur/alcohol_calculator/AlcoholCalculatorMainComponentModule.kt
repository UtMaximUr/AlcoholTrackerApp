package com.utmaximur.alcohol_calculator

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [AlcoholCalculatorDomainMainModule::class])
@ComponentScan
class AlcoholCalculatorMainComponentModule
