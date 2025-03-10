package com.utmaximur.core.logging

import com.utmaximur.app.base.app.ApplicationInfo
import org.koin.core.annotation.Single

@Single
fun provideIosLogger(applicationInfo: ApplicationInfo): Logger = KermitLogger(applicationInfo)
