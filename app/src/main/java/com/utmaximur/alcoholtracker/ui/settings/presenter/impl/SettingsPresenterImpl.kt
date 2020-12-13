package com.utmaximur.alcoholtracker.ui.settings.presenter.impl

import android.content.Context

import com.utmaximur.alcoholtracker.ui.base.BasePresenter
import com.utmaximur.alcoholtracker.ui.settings.presenter.SettingsPresenter
import com.utmaximur.alcoholtracker.ui.settings.view.SettingsView


class SettingsPresenterImpl(private val context: Context): BasePresenter<SettingsView>(),
    SettingsPresenter {

    override fun viewIsReady() {}

    override fun onPrivacyPolicyClicked() {
        view?.showPrivacyPolicy()
    }

    override fun onTermsOfUseClicked() {
        view?.showTermsOfUse()
    }

    override fun onRateUsClicked() {
        view?.rateUs()
    }
}