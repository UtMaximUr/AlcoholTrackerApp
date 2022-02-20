package com.utmaximur.buildsrc

object Libs {

    object Core {
        private const val core_ktx = "1.7.0"
        private const val multidex_version = "2.0.1"
        private const val appcompat_version = "1.4.1"
        private const val material_version = "1.5.0"

        const val coreKtx = "androidx.core:core-ktx:$core_ktx"
        const val multidex = "androidx.multidex:multidex:$multidex_version"
        const val appcompat = "androidx.appcompat:appcompat:$appcompat_version"
        const val material =  "com.google.android.material:material:$material_version"
    }

    object Compose {
        // compose
        private const val compose_version = "1.2.0-alpha02"

        const val runtime = "androidx.compose.runtime:runtime:$compose_version"
        const val ui_test = "androidx.compose.ui:ui-test-junit4:$compose_version"
        const val ui_tooling = "androidx.compose.ui:ui-tooling:$compose_version"
        const val ui = "androidx.compose.ui:ui:$compose_version"
        const val material = "androidx.compose.material:material:$compose_version"
        const val preview = "androidx.compose.ui:ui-tooling-preview:$compose_version"
        const val activity = "androidx.activity:activity-compose:1.4.0"
        const val livedata = "androidx.compose.runtime:runtime-livedata:$compose_version"
        const val pager = "com.google.accompanist:accompanist-pager:0.24.2-alpha"
        const val drawable_painter = "com.google.accompanist:accompanist-drawablepainter:0.16.0"
        const val coil = "io.coil-kt:coil-compose:1.3.1"
        const val placeholder = "com.google.accompanist:accompanist-placeholder:0.24.1-alpha"
        const val glide = "com.github.skydoves:landscapist-glide:1.4.5"
        const val view_model_compose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-alpha01"
        const val system_ui_controller = "com.google.accompanist:accompanist-systemuicontroller:0.24.2-alpha"
        const val permissions = "com.google.accompanist:accompanist-permissions:0.24.2-alpha"
    }

    object Navigation {
        private const val nav_version = "2.5.0-alpha01"
        const val navigation_compose = "androidx.navigation:navigation-compose:$nav_version"
    }

    object Hilt {
        private const val hilt_version = "2.40.5"
        private const val hilt_compose_version = "1.0.0"
        const val hilt_android = "com.google.dagger:hilt-android:$hilt_version"
        const val hilt_compiler = "com.google.dagger:hilt-compiler:$hilt_version"
        const val hilt_compose = "androidx.hilt:hilt-navigation-compose:$hilt_compose_version"
//        const val hilt_viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
//        kapt("com.google.dagger:hilt-android-compiler:2.38.1")
    }

    object Json {
        private const val gson_version = "2.8.9"
        const val gson = "com.google.code.gson:gson:$gson_version"
    }

    object Room {
        private const val room_version = "2.4.1"
        const val room = "androidx.room:room-runtime:$room_version"
        const val room_ktx = "androidx.room:room-ktx:$room_version"
        const val room_compiler = "androidx.room:room-compiler:$room_version"
    }

    object Timber {
        private const val timber_version = "5.0.1"
        const val timber = "com.jakewharton.timber:timber:$timber_version"
    }

    object FireBase {
        private const val firebase_version = "26.1.1"
        const val firebase = "com.google.firebase:firebase-bom:$firebase_version"
        const val firebase_crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
        const val firebase_analytics = "com.google.firebase:firebase-analytics-ktx"
    }

    object InApUpdates {
        private const val updates_version = "1.10.3"
        const val in_ap_updates = "com.google.android.play:core:$updates_version"
    }
}


//implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.4.0'
//testImplementation 'junit:junit:4.+'
//androidTestImplementation 'androidx.test.ext:junit:1.1.3'
//androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'


