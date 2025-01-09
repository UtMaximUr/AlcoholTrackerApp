package com.utmaximur.drink

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [StatisticDrinkDomainModule::class])
@ComponentScan
class StatisticDrinkComponentModule