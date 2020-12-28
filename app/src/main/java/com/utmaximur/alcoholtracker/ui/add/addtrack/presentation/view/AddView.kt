package com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.view

import com.utmaximur.alcoholtracker.ui.base.MvpView

interface AddView : MvpView {

    fun getIdDrink(): String

    fun getDrink(): String

    fun getQuantity(): Int

    fun getDegree(): String

    fun getPrice(): Float

    fun getDate(): Long

    fun getVolume(): String

    fun getVolumeList(): List<String>

    fun getIcon(): Int

    fun setVolume(volume: List<String>)

    fun setDegreeList(degree: List<String?>)

    fun getDegreeList(): List<String?>

    fun showWarningEmptyField()

}