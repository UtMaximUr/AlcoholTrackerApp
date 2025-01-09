package com.utmaximur.currency

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [CurrencyDomainMainModule::class])
@ComponentScan
class CurrencyComponentModule