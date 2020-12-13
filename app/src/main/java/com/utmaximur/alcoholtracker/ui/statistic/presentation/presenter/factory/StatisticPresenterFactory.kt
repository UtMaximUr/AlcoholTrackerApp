package com.utmaximur.alcoholtracker.ui.statistic.presentation.presenter.factory

import android.content.Context
import com.utmaximur.alcoholtracker.ui.calendar.presentation.presenter.impl.CalendarPresenterImpl
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.ui.calendar.interactor.CalendarInteractor
import com.utmaximur.alcoholtracker.ui.calendar.interactor.impl.CalendarInteractorImpl
import com.utmaximur.alcoholtracker.ui.statistic.interactor.StatisticInteractor
import com.utmaximur.alcoholtracker.ui.statistic.interactor.impl.StatisticInteractorImpl
import com.utmaximur.alcoholtracker.ui.statistic.presentation.presenter.impl.StatisticPresenterImpl

class StatisticPresenterFactory {

    companion object {
        fun createPresenter(context: Context): StatisticPresenterImpl {
            val interactor: StatisticInteractor = StatisticInteractorImpl(App.getStorageManager(context))
            return StatisticPresenterImpl(interactor)
        }
    }
}