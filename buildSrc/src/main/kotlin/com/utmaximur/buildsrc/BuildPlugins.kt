package com.utmaximur.buildsrc

object BuildPlugins {

    const val compose_version = "1.2.0-alpha02"
    const val kotlin_version = "1.6.10"
    private const val gradle_version = "7.0.4"
    private const val google_service_version = "4.3.10"
    private const val firebase_version = "2.5.2"
    private const val hilt_version = "2.40.5"

    const val gradle = "com.android.tools.build:gradle:$gradle_version"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    const val google_service = "com.google.gms:google-services:$google_service_version"
    const val firebase = "com.google.firebase:firebase-crashlytics-gradle:$firebase_version"
    const val hilt =  "com.google.dagger:hilt-android-gradle-plugin:$hilt_version"
}