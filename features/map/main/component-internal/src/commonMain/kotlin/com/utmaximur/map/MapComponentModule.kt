package com.utmaximur.map

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [MapDomainMainModule::class])
@ComponentScan
class MapComponentModule
