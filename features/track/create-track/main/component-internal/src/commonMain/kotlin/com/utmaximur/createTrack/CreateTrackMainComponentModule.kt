package com.utmaximur.createTrack

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [CreateTrackDomainMainModule::class])
@ComponentScan
class CreateTrackMainComponentModule