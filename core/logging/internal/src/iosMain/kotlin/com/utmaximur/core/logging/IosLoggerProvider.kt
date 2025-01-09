package com.utmaximur.core.logging

import org.koin.core.annotation.Single
import com.utmaximur.app.base.app.ApplicationInfo
import com.utmaximur.core.logging.KermitLogger
import com.utmaximur.core.logging.Logger

@Single
fun provideIosLogger(applicationInfo: ApplicationInfo): Logger = KermitLogger(applicationInfo)