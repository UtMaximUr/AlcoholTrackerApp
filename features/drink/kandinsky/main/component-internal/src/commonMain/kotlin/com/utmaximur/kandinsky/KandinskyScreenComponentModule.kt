package com.utmaximur.kandinsky

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [KandinskyScreenDomainMainModule::class])
@ComponentScan
class KandinskyScreenComponentModule