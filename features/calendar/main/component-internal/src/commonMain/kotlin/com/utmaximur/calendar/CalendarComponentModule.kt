package com.utmaximur.calendar

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [CalendarDomainMainModule::class])
@ComponentScan
class CalendarComponentModule