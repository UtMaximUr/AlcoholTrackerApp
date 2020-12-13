package com.utmaximur.alcoholtracker.ui.settings.presenter.factory

import android.content.Context
import com.utmaximur.alcoholtracker.ui.settings.presenter.impl.SettingsPresenterImpl

class SettingsPresenterFactory {
    companion object {
        fun createPresenter(context: Context): SettingsPresenterImpl {
            return SettingsPresenterImpl(context)
        }
    }
}