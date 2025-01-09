plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
    id(libs.plugins.cocoapods.get().pluginId)
}

kotlin {
    cocoapods {
        summary = "di app framework"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        framework {
            baseName = "di"
            isStatic = true
            export(libs.arkivanov.decompose)
            export(libs.arkivanov.essenty.lifecycle)
        }
    }
    sourceSets {
        commonMain.dependencies {
            implementation(libs.arkivanov.mvikotlin.main)
            implementation(libs.arkivanov.mvi)
            implementation(libs.arkivanov.mvikotlin.logging)
            api(libs.arkivanov.decompose)
            api(libs.arkivanov.essenty.lifecycle)

            implementation(projects.core.base)
            implementation(projects.core.logging.internal)
            implementation(projects.core.analytics.tracker.internal)
            implementation(projects.core.analytics.firebase)

            implementation(projects.coreData.settingsManager.internal)
            implementation(projects.coreData.databaseRoom)
            implementation(projects.coreData.remote.client.base)
            implementation(projects.coreData.local.drinkManager.internal)
            implementation(projects.coreData.local.trackManager.internal)

            implementation(projects.features.root.component)
            implementation(projects.features.root.domain)
            implementation(projects.features.message.data)
            implementation(projects.features.calendar.navigation.componentInternal)
            implementation(projects.features.calendar.main.componentInternal)
            implementation(projects.features.calendar.data)
            implementation(projects.features.statistic.data)
            implementation(projects.features.statistic.main.componentInternal)
            implementation(projects.features.statistic.money.main.componentInternal)
            implementation(projects.features.statistic.drink.main.componentInternal)
            implementation(projects.features.statistic.day.main.componentInternal)
            implementation(projects.features.track.createTrack.navigation.componentInternal)
            implementation(projects.features.track.createTrack.main.componentInternal)
            implementation(projects.features.track.createTrack.data)
            implementation(projects.features.track.calculator.main.componentInternal)
            implementation(projects.features.track.calculator.data)
            implementation(projects.features.track.datePicker.main.componentInternal)
            implementation(projects.features.track.datePicker.data)
            implementation(projects.features.settings.data)
            implementation(projects.features.settings.navigation.componentInternal)
            implementation(projects.features.settings.main.componentInternal)
            implementation(projects.features.settings.currencyModal.componentInternal)
            implementation(projects.features.track.detailTrack.navigation.componentInternal)
            implementation(projects.features.track.detailTrack.main.componentInternal)
            implementation(projects.features.track.detailTrack.data)
            implementation(projects.features.confirmDialog.main.componentInternal)
            implementation(projects.features.confirmDialog.data)
            implementation(projects.features.splashScreen.data)
            implementation(projects.features.splashScreen.main.componentInternal)
            implementation(projects.features.drink.createDrink.navigation.componentInternal)
            implementation(projects.features.drink.createDrink.main.componentInternal)
            implementation(projects.features.drink.createDrink.data)
            implementation(projects.features.drink.drinkImageModal.main.componentInternal)
            implementation(projects.features.drink.drinkImageModal.data)
        }
        androidMain.dependencies {
            implementation(libs.androidx.startup.runtime)
            implementation(libs.koin.android)
        }
    }
}