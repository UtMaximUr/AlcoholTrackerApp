package com.utmaximur.alcoholtracker.ui.base

// базовый класс, который будет наследоваться всеми презентерами.
// В нем реализованы общие методы по работе с view
abstract class BasePresenter<T : MvpView> :
    MvpPresenter<T> {

    var view: T? = null

    override fun onAttachView(mvpView: T) {
        view = mvpView
    }

    override fun onDetachView() {
        view = null
    }
}