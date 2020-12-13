package com.utmaximur.alcoholtracker.ui.calendar.presentation.presenter.factory

import android.content.Context
import com.utmaximur.alcoholtracker.ui.calendar.presentation.presenter.impl.CalendarPresenterImpl
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.ui.calendar.interactor.CalendarInteractor
import com.utmaximur.alcoholtracker.ui.calendar.interactor.impl.CalendarInteractorImpl

class CalendarPresenterFactory {

    companion object {
        fun createPresenter(context: Context): CalendarPresenterImpl {
            val interactor: CalendarInteractor = CalendarInteractorImpl(App.getStorageManager(context))
            return CalendarPresenterImpl(interactor)
        }
    }
}