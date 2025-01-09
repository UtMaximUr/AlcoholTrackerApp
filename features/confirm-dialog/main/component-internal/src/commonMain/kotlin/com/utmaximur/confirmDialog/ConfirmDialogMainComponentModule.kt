package com.utmaximur.confirmDialog

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [ConfirmDialogDomainMainModule::class])
@ComponentScan
class ConfirmDialogMainComponentModule