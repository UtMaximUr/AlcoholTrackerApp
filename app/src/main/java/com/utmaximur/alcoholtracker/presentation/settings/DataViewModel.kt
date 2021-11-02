package com.utmaximur.alcoholtracker.presentation.settings

import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.data.repository.DataRepository
import javax.inject.Inject

class DataViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {

    fun onBackupClick() = dataRepository.backupDatabase()

    fun onRestoreClick() {
        dataRepository.restoreDatabase(null)
    }

    fun onDeleteClick() {

    }
}