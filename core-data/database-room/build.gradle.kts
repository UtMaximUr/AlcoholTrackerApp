plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.koin.platform)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.room)
}

kotlin {
    sourceSets.commonMain {
        //kotlin.srcDirs("build/generated/ksp/metadata")
        //kotlin.srcDir("build/generated/ksp/metadata")
    }
}
dependencies {
    commonMainImplementation(libs.kotlinx.datetime)
    commonMainImplementation(libs.sqlite.bundled)
    commonMainImplementation(libs.androidx.room.runtime)
    commonMainImplementation(libs.kotlinx.serialization.json)
    commonMainImplementation(libs.kotlinx.serialization.core)

    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
}


room {
    schemaDirectory("$projectDir/schemas")
}

