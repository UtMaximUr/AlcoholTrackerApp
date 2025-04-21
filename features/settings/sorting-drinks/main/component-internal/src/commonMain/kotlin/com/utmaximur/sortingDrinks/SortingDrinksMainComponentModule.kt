package com.utmaximur.sortingDrinks

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [SortingDrinksDomainMainModule::class])
@ComponentScan
class SortingDrinksMainComponentModule
