package com.utmaximur.actions

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [ActionsImageDomainMainModule::class])
@ComponentScan
class ActionImageComponentModule