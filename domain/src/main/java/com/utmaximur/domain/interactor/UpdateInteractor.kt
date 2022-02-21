package com.utmaximur.domain.interactor

import android.app.Activity
import com.utmaximur.domain.repository.PreferencesRepository
import com.utmaximur.domain.repository.UpdateRepository
import javax.inject.Inject

class UpdateInteractor @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val updateRepository: UpdateRepository
) {

    fun isSavedUpdate(): Boolean {
        return preferencesRepository.isUpdateChecked()
    }

    fun update(activity: Activity, onShowDialog: () -> Unit) {
        updateRepository.update(activity, onShowDialog)
    }

    fun completeUpdate() {
        updateRepository.completeUpdate()
    }
}