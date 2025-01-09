package com.utmaximur.settings

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [SettingsDomainMainModule::class])
@ComponentScan
class SettingsMainComponentModule