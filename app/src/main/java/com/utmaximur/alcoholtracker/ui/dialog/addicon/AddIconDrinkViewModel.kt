package com.utmaximur.alcoholtracker.ui.dialog.addicon

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.data.model.Icon
import com.utmaximur.alcoholtracker.repository.IconRepository

class AddIconDrinkViewModel(private var iconRepository: IconRepository): ViewModel() {

    fun getIcons(): LiveData<MutableList<Icon>> {
        return  iconRepository.getIcons()
    }
}