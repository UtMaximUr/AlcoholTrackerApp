package com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.presenter.factory

import android.content.Context
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.ui.add.addtrack.interactor.AddInteractor
import com.utmaximur.alcoholtracker.ui.add.addtrack.interactor.impl.AddInteractorImpl
import com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.presenter.impl.AddPresenterImpl

class AddPresenterFactory {

    companion object {
        fun createPresenter(context: Context): AddPresenterImpl {
            val interactor: AddInteractor = AddInteractorImpl(App.getStorageManager(context))
            return AddPresenterImpl(interactor)
        }
    }
}