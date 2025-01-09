package com.utmaximur.createDrink

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [CreateDrinkDomainMainModule::class])
@ComponentScan
class CreateDrinkMainComponentModule