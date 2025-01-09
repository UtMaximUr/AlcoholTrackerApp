package com.utmaximur.money

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [StatisticMoneyDomainModule::class])
@ComponentScan
class StatisticMoneyComponentModule