enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("gradle/build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

fun includeFeature(projectName: String) {
    include("features:$projectName")
}

fun includeCoreFeature(projectName: String) {
    include("core:$projectName")
}

fun includeCoreData(projectName: String) {
    include("core-data:$projectName")
}

rootProject.name = "AlcoholTracker"
include(":composeApp")
include(":di")
include(":domain")

includeCoreData("settings-manager:api")
includeCoreData("settings-manager:internal")
includeCoreData("database-room")
includeCoreData("remote:client:api")
includeCoreData("remote:client:auth")
includeCoreData("remote:client:base")
includeCoreData("remote:errors")
includeCoreData("local:drink-manager:api")
includeCoreData("local:drink-manager:internal")
includeCoreData("local:track-manager:api")
includeCoreData("local:track-manager:internal")
includeCoreData("models")

includeCoreFeature("base")
includeCoreFeature("decompose")
includeCoreFeature("logging:api")
includeCoreFeature("logging:internal")
includeCoreFeature("design")
includeCoreFeature("utils")
includeCoreFeature("analytics:domain")
includeCoreFeature("analytics:params")
includeCoreFeature("analytics:tracker:api")
includeCoreFeature("analytics:tracker:internal")
includeCoreFeature("analytics:firebase")
includeCoreFeature("mvi-mappers:api")
includeCoreFeature("mvi-mappers:internal")
includeCoreFeature("permission")
includeCoreFeature("media:compose")
includeCoreFeature("media:core")

includeFeature("message:data")
includeFeature("message:domain")
includeFeature("message:message-component")
includeFeature("root:component")
includeFeature("root:domain")

includeFeature("calendar:data")
includeFeature("calendar:domain")
includeFeature("calendar:navigation:component-api")
includeFeature("calendar:navigation:component-internal")
includeFeature("calendar:main:domain")
includeFeature("calendar:main:component-api")
includeFeature("calendar:main:component-internal")

includeFeature("statistic:data")
includeFeature("statistic:domain")
includeFeature("statistic:main:component-api")
includeFeature("statistic:main:component-internal")
includeFeature("statistic:day:main:domain")
includeFeature("statistic:day:main:component-api")
includeFeature("statistic:day:main:component-internal")
includeFeature("statistic:drink:main:domain")
includeFeature("statistic:drink:main:component-api")
includeFeature("statistic:drink:main:component-internal")
includeFeature("statistic:money:main:domain")
includeFeature("statistic:money:main:component-api")
includeFeature("statistic:money:main:component-internal")

includeFeature("track:create-track:data")
includeFeature("track:create-track:domain")
includeFeature("track:create-track:navigation:component-api")
includeFeature("track:create-track:navigation:component-internal")
includeFeature("track:create-track:main:domain")
includeFeature("track:create-track:main:component-api")
includeFeature("track:create-track:main:component-internal")

includeFeature("track:detail-track:data")
includeFeature("track:detail-track:domain")
includeFeature("track:detail-track:navigation:component-api")
includeFeature("track:detail-track:navigation:component-internal")
includeFeature("track:detail-track:main:domain")
includeFeature("track:detail-track:main:component-api")
includeFeature("track:detail-track:main:component-internal")

includeFeature("confirm-dialog:data")
includeFeature("confirm-dialog:domain")
includeFeature("confirm-dialog:main:domain")
includeFeature("confirm-dialog:main:component-api")
includeFeature("confirm-dialog:main:component-internal")

includeFeature("track:calculator:data")
includeFeature("track:calculator:domain")
includeFeature("track:calculator:main:domain")
includeFeature("track:calculator:main:component-api")
includeFeature("track:calculator:main:component-internal")

includeFeature("track:date-picker:data")
includeFeature("track:date-picker:domain")
includeFeature("track:date-picker:main:domain")
includeFeature("track:date-picker:main:component-api")
includeFeature("track:date-picker:main:component-internal")

includeFeature("settings:navigation:component-api")
includeFeature("settings:navigation:component-internal")
includeFeature("settings:data")
includeFeature("settings:domain")
includeFeature("settings:main:domain")
includeFeature("settings:main:component-api")
includeFeature("settings:main:component-internal")
includeFeature("settings:currency-modal:component-api")
includeFeature("settings:currency-modal:component-internal")
includeFeature("settings:currency-modal:domain")

includeFeature("splash-screen:data")
includeFeature("splash-screen:domain")
includeFeature("splash-screen:main:domain")
includeFeature("splash-screen:main:component-api")
includeFeature("splash-screen:main:component-internal")

includeFeature("drink:create-drink:data")
includeFeature("drink:create-drink:domain")
includeFeature("drink:create-drink:navigation:component-api")
includeFeature("drink:create-drink:navigation:component-internal")
includeFeature("drink:create-drink:main:domain")
includeFeature("drink:create-drink:main:component-api")
includeFeature("drink:create-drink:main:component-internal")

includeFeature("drink:drink-image-modal:data")
includeFeature("drink:drink-image-modal:domain")
includeFeature("drink:drink-image-modal:main:component-api")
includeFeature("drink:drink-image-modal:main:component-internal")
includeFeature("drink:drink-image-modal:main:domain")
