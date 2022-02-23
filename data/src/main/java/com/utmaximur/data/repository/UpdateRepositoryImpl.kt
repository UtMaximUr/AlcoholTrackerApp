package com.utmaximur.data.repository

import android.app.Activity
import com.utmaximur.data.update.UpdateManager
import com.utmaximur.domain.repository.UpdateRepository
import javax.inject.Inject

class UpdateRepositoryImpl @Inject constructor(private val updateManager: UpdateManager) : UpdateRepository {

    override fun update(activity: Activity, onShowDialog: () -> Unit) {
        updateManager.update(activity, onShowDialog)
    }

    override fun completeUpdate() {
        updateManager.completeUpdate()
    }
}