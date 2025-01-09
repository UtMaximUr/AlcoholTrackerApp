package com.utmaximur.splash

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [SplashScreenDomainMainModule::class])
@ComponentScan
class SplashScreenComponentModule