package com.utmaximur.alcoholtracker.ui.settings.view

import com.utmaximur.alcoholtracker.ui.base.MvpView


interface SettingsView: MvpView {

    fun showPrivacyPolicy()

    fun showTermsOfUse()

    fun rateUs()
}