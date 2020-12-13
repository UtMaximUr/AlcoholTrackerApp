package com.utmaximur.alcoholtracker.ui.settings.presenter

import com.utmaximur.alcoholtracker.ui.settings.view.SettingsView
import com.utmaximur.alcoholtracker.ui.base.MvpPresenter


interface SettingsPresenter: MvpPresenter<SettingsView> {


    fun onPrivacyPolicyClicked()

    fun onTermsOfUseClicked()

    fun onRateUsClicked()

}