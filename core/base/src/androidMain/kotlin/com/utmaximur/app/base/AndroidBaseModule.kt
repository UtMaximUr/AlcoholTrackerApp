package com.utmaximur.app.base

import android.content.Context
import android.content.pm.PackageManager
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Single
import com.utmaximur.app.base.app.ApplicationInfo
import com.utmaximur.app.base.app.Flavor

@Factory
fun provideFlavor() = Flavor.Qa

@Factory
fun providePackageManager(context: Context): PackageManager = context.packageManager

@Single
fun provideApplicationInfo(flavor: Flavor, packageManager: PackageManager, context: Context): ApplicationInfo {
    val applicationInfo = packageManager.getApplicationInfo(context.packageName, 0)
    val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
    return ApplicationInfo(
        packageName = context.packageName,
        debugBuild = (applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE) != 0,
        flavor = flavor,
        versionName = packageInfo.versionName,
        versionCode = @Suppress("DEPRECATION") packageInfo.versionCode
    )
}