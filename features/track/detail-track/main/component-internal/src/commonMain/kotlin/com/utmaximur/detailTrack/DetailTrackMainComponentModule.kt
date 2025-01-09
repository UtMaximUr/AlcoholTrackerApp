package com.utmaximur.detailTrack

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DetailTrackDomainMainModule::class])
@ComponentScan
class DetailTrackMainComponentModule