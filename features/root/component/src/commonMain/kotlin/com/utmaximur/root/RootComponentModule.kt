package com.utmaximur.root

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import com.utmaximur.message.MessageModule

@Module(includes = [MessageModule::class])
@ComponentScan
class RootComponentModule