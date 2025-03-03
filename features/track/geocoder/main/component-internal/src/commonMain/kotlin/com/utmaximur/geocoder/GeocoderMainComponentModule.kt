package com.utmaximur.geocoder

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [GeocoderDomainMainModule::class])
@ComponentScan
class GeocoderMainComponentModule