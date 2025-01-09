package com.utmaximur.app.base

import org.koin.core.annotation.Factory
import org.koin.core.annotation.Single
import platform.Foundation.NSBundle
import com.utmaximur.app.base.app.ApplicationInfo
import com.utmaximur.app.base.app.Flavor
import kotlin.experimental.ExperimentalNativeApi

@Factory
fun provideFlavor() = Flavor.Qa

// @Single
// fun provideNSUser(): NSUserDefaults = NSUserDefaults.standardUserDefaults

@OptIn(ExperimentalNativeApi::class)
@Single
fun provideApplicationInfo(flavor: Flavor) = ApplicationInfo(
    packageName = NSBundle.mainBundle.bundleIdentifier ?: error("Bundle ID not found"),
    debugBuild = Platform.isDebugBinary,
    flavor = flavor,
    versionName = NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String ?: "",
    versionCode = (NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion") as? String)?.toIntOrNull() ?: 0
)