package com.utmaximur.datePicker

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DatePickerDomainMainModule::class])
@ComponentScan
class DatePickerMainComponentModule