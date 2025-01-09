package com.utmaximur.day

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [StatisticDayDomainModule::class])
@ComponentScan
class StatisticDayComponentModule